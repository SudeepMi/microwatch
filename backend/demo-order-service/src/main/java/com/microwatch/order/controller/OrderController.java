package com.microwatch.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {

    private final AtomicBoolean isHealthy = new AtomicBoolean(true);

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealth() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "order-service");
        response.put("status", isHealthy.get() ? "UP" : "DOWN");
        response.put("version", "1.0.0");
        response.put("timestamp", Instant.now().toString());

        if (isHealthy.get()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(503).body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "Order Service");
        response.put("description", "Microservice managing order creation, processing, and status history");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/failure")
    public ResponseEntity<Map<String, Object>> simulateFailure() {
        isHealthy.set(false);
        Map<String, Object> response = new HashMap<>();
        response.put("service", "order-service");
        response.put("status", "DOWN");
        response.put("message", "Simulated failure activated");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/recover")
    public ResponseEntity<Map<String, Object>> simulateRecovery() {
        isHealthy.set(true);
        Map<String, Object> response = new HashMap<>();
        response.put("service", "order-service");
        response.put("status", "UP");
        response.put("message", "Simulated recovery activated");
        return ResponseEntity.ok(response);
    }
}
