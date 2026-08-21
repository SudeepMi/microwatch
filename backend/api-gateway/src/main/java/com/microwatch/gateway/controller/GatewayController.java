package com.microwatch.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microwatch.gateway.websocket.MonitoringWebSocketHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class GatewayController {

    @Value("${registry.service.url:http://localhost:8082}")
    private String registryServiceUrl;

    @Value("${monitoring.service.url:http://localhost:8081}")
    private String monitoringServiceUrl;

    @Value("${notification.service.url:http://localhost:8084}")
    private String notificationServiceUrl;

    private final MonitoringWebSocketHandler webSocketHandler;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GatewayController(MonitoringWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping("/api/v1/internal/broadcast-event")
    public ResponseEntity<Void> broadcastEvent(@RequestBody Object eventPayload) {
        try {
            String json = objectMapper.writeValueAsString(eventPayload);
            webSocketHandler.broadcast(json);
        } catch (Exception e) {
            System.err.println("Error serializing WS event: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/api/v1/services/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<byte[]> proxyRegistryService(HttpServletRequest request, @RequestBody(required = false) byte[] body) {
        String uri = request.getRequestURI();
        // Route /api/v1/services/{id}/events and /api/v1/services/{id}/metrics to Monitoring Service
        if (uri.contains("/events") || uri.contains("/metrics")) {
            return forwardRequest(request, body, monitoringServiceUrl);
        }
        return forwardRequest(request, body, registryServiceUrl);
    }

    @RequestMapping(value = {"/api/v1/dashboard/**", "/api/v1/metrics/**", "/api/v1/events/**"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<byte[]> proxyMonitoringService(HttpServletRequest request, @RequestBody(required = false) byte[] body) {
        return forwardRequest(request, body, monitoringServiceUrl);
    }

    @RequestMapping(value = "/api/v1/notifications/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<byte[]> proxyNotificationService(HttpServletRequest request, @RequestBody(required = false) byte[] body) {
        return forwardRequest(request, body, notificationServiceUrl);
    }

    private ResponseEntity<byte[]> forwardRequest(HttpServletRequest request, byte[] body, String targetBaseUrl) {
        try {
            String path = request.getRequestURI();
            String query = request.getQueryString();
            String fullTargetUrl = targetBaseUrl + path + (query != null ? "?" + query : "");

            HttpMethod method = HttpMethod.valueOf(request.getMethod());
            HttpHeaders headers = new HttpHeaders();

            Collections.list(request.getHeaderNames()).forEach(headerName -> {
                String lower = headerName.toLowerCase();
                if (!lower.equals("host") && !lower.equals("content-length") && !lower.equals("transfer-encoding")) {
                    headers.add(headerName, request.getHeader(headerName));
                }
            });

            HttpEntity<byte[]> httpEntity = new HttpEntity<>(body, headers);
            ResponseEntity<byte[]> response = restTemplate.exchange(new URI(fullTargetUrl), method, httpEntity, byte[].class);

            HttpHeaders cleanResponseHeaders = filterHeaders(response.getHeaders());
            return new ResponseEntity<>(response.getBody(), cleanResponseHeaders, response.getStatusCode());

        } catch (HttpStatusCodeException e) {
            HttpHeaders cleanResponseHeaders = filterHeaders(e.getResponseHeaders());
            return new ResponseEntity<>(e.getResponseBodyAsByteArray(), cleanResponseHeaders, e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("{\"error\":\"GATEWAY_FORWARD_ERROR\",\"message\":\"" + e.getMessage() + "\"}").getBytes());
        }
    }

    private HttpHeaders filterHeaders(HttpHeaders inputHeaders) {
        HttpHeaders filtered = new HttpHeaders();
        if (inputHeaders != null) {
            inputHeaders.forEach((key, values) -> {
                String lowerKey = key.toLowerCase();
                if (!lowerKey.equals("transfer-encoding") &&
                    !lowerKey.equals("content-length") &&
                    !lowerKey.equals("connection") &&
                    !lowerKey.equals("keep-alive") &&
                    !lowerKey.equals("host") &&
                    !lowerKey.equals("server")) {
                    filtered.put(key, values);
                }
            });
        }
        return filtered;
    }
}
