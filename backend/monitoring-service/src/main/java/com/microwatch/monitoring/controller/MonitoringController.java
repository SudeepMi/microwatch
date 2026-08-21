package com.microwatch.monitoring.controller;

import com.microwatch.monitoring.dto.DashboardSummaryDto;
import com.microwatch.monitoring.model.Metric;
import com.microwatch.monitoring.model.ServiceEvent;
import com.microwatch.monitoring.service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/dashboard/summary")
    public ResponseEntity<DashboardSummaryDto> getDashboardSummary() {
        return ResponseEntity.ok(monitoringService.getDashboardSummary());
    }

    @GetMapping("/metrics/latest")
    public ResponseEntity<List<Map<String, Object>>> getLatestMetrics() {
        return ResponseEntity.ok(monitoringService.getLatestMetrics());
    }

    @GetMapping("/services/{id}/metrics")
    public ResponseEntity<List<Metric>> getServiceMetrics(@PathVariable("id") Long id,
                                                         @RequestParam(value = "period", required = false) String period) {
        return ResponseEntity.ok(monitoringService.getServiceMetrics(id, period));
    }

    @GetMapping("/services/{id}/events")
    public ResponseEntity<List<ServiceEvent>> getServiceEvents(@PathVariable("id") Long id) {
        return ResponseEntity.ok(monitoringService.getServiceEvents(id));
    }

    @GetMapping("/events/recent")
    public ResponseEntity<List<ServiceEvent>> getRecentEvents() {
        return ResponseEntity.ok(monitoringService.getRecentEvents());
    }
}
