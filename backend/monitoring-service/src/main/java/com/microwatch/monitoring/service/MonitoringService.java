package com.microwatch.monitoring.service;

import com.microwatch.monitoring.dto.DashboardSummaryDto;
import com.microwatch.monitoring.dto.StatusChangeEventDto;
import com.microwatch.monitoring.model.Metric;
import com.microwatch.monitoring.model.ServiceEntity;
import com.microwatch.monitoring.model.ServiceEvent;
import com.microwatch.monitoring.repository.MetricRepository;
import com.microwatch.monitoring.repository.ServiceEventRepository;
import com.microwatch.monitoring.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitoringService {

    private final ServiceRepository serviceRepository;
    private final MetricRepository metricRepository;
    private final ServiceEventRepository serviceEventRepository;
    private final RestTemplate restTemplate;

    @Value("${notification.service.url:http://localhost:8084}")
    private String notificationServiceUrl;

    @Value("${api.gateway.url:http://localhost:8080}")
    private String apiGatewayUrl;

    // Cache last status per service to detect status transitions (UP <-> DOWN)
    private final Map<Long, String> lastKnownStatus = new ConcurrentHashMap<>();

    public MonitoringService(ServiceRepository serviceRepository,
                             MetricRepository metricRepository,
                             ServiceEventRepository serviceEventRepository) {
        this.serviceRepository = serviceRepository;
        this.metricRepository = metricRepository;
        this.serviceEventRepository = serviceEventRepository;
        this.restTemplate = new RestTemplate();
    }

    public void checkAllServices() {
        List<ServiceEntity> services = serviceRepository.findAll();
        for (ServiceEntity service : services) {
            checkServiceHealth(service);
        }
    }

    private void checkServiceHealth(ServiceEntity service) {
        String healthUrl = service.getUrl() + service.getHealthEndpoint();
        long startTime = System.currentTimeMillis();
        String currentStatus = "DOWN";
        Long responseTime = null;

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(healthUrl, Map.class);
            long endTime = System.currentTimeMillis();
            responseTime = endTime - startTime;

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object statusObj = response.getBody().get("status");
                if (statusObj != null && "UP".equalsIgnoreCase(statusObj.toString())) {
                    currentStatus = "UP";
                }
            }
        } catch (Exception e) {
            currentStatus = "DOWN";
            responseTime = null;
        }

        // Save metric record
        Metric metric = new Metric(service.getId(), currentStatus, responseTime);
        metricRepository.save(metric);

        // Track status transition
        String previousStatus = lastKnownStatus.get(service.getId());
        if (previousStatus == null) {
            // First run for this service
            lastKnownStatus.put(service.getId(), currentStatus);
        } else if (!previousStatus.equalsIgnoreCase(currentStatus)) {
            // Status changed!
            lastKnownStatus.put(service.getId(), currentStatus);
            handleStatusChange(service, previousStatus, currentStatus, responseTime);
        }
    }

    private void handleStatusChange(ServiceEntity service, String previousStatus, String currentStatus, Long responseTime) {
        String message = String.format("Service '%s' changed status from %s to %s", service.getName(), previousStatus, currentStatus);
        
        // 1. Log Service Event
        ServiceEvent event = new ServiceEvent(service.getId(), previousStatus, currentStatus, message);
        serviceEventRepository.save(event);

        // 2. Prepare event DTO
        StatusChangeEventDto eventDto = new StatusChangeEventDto(service.getId(), service.getName(), currentStatus, responseTime);

        // 3. Notify Notification Service
        try {
            String targetNotificationUrl = notificationServiceUrl + "/api/v1/notifications/events";
            restTemplate.postForEntity(targetNotificationUrl, eventDto, Void.class);
        } catch (Exception e) {
            System.err.println("Failed to reach notification service: " + e.getMessage());
        }

        // 4. Notify API Gateway to broadcast WebSocket message
        try {
            String targetWsBroadcastUrl = apiGatewayUrl + "/api/v1/internal/broadcast-event";
            restTemplate.postForEntity(targetWsBroadcastUrl, eventDto, Void.class);
        } catch (Exception e) {
            System.err.println("Failed to broadcast WS event via Gateway: " + e.getMessage());
        }
    }

    public DashboardSummaryDto getDashboardSummary() {
        List<ServiceEntity> services = serviceRepository.findAll();
        long totalServices = services.size();
        long healthyServices = 0;
        long downServices = 0;
        long totalResponseTime = 0;
        long responseTimeCount = 0;

        List<Metric> latestMetrics = metricRepository.findLatestMetricsPerService();
        for (Metric metric : latestMetrics) {
            if ("UP".equalsIgnoreCase(metric.getStatus())) {
                healthyServices++;
                if (metric.getResponseTime() != null) {
                    totalResponseTime += metric.getResponseTime();
                    responseTimeCount++;
                }
            } else {
                downServices++;
            }
        }

        double avgResponseTime = responseTimeCount > 0 ? (double) totalResponseTime / responseTimeCount : 0.0;
        double overallUptime = totalServices > 0 ? ((double) healthyServices / totalServices) * 100.0 : 100.0;

        return new DashboardSummaryDto(totalServices, healthyServices, downServices, 
                Math.round(avgResponseTime * 10.0) / 10.0, 
                Math.round(overallUptime * 10.0) / 10.0);
    }

    public List<Map<String, Object>> getLatestMetrics() {
        List<ServiceEntity> services = serviceRepository.findAll();
        List<Metric> latestMetrics = metricRepository.findLatestMetricsPerService();

        Map<Long, Metric> metricMap = new HashMap<>();
        for (Metric m : latestMetrics) {
            metricMap.put(m.getServiceId(), m);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (ServiceEntity s : services) {
            Map<String, Object> map = new HashMap<>();
            map.put("serviceId", s.getId());
            map.put("name", s.getName());
            map.put("url", s.getUrl());
            map.put("description", s.getDescription());

            Metric m = metricMap.get(s.getId());
            if (m != null) {
                map.put("status", m.getStatus());
                map.put("responseTime", m.getResponseTime());
                map.put("checkedAt", m.getCheckedAt().toString());
            } else {
                map.put("status", "UNKNOWN");
                map.put("responseTime", null);
                map.put("checkedAt", null);
            }
            result.add(map);
        }
        return result;
    }

    public List<Metric> getServiceMetrics(Long serviceId, String period) {
        if (!serviceRepository.existsById(serviceId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found");
        }
        if ("24h".equalsIgnoreCase(period)) {
            LocalDateTime since = LocalDateTime.now().minusHours(24);
            return metricRepository.findByServiceIdAndCheckedAtAfterOrderByCheckedAtDesc(serviceId, since);
        }
        return metricRepository.findByServiceIdOrderByCheckedAtDesc(serviceId);
    }

    public List<ServiceEvent> getServiceEvents(Long serviceId) {
        return serviceEventRepository.findByServiceIdOrderByCreatedAtDesc(serviceId);
    }

    public List<ServiceEvent> getRecentEvents() {
        return serviceEventRepository.findTop10ByOrderByCreatedAtDesc();
    }
}
