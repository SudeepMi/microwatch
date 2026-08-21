package com.microwatch.notification.controller;

import com.microwatch.notification.dto.EventDto;
import com.microwatch.notification.model.Notification;
import com.microwatch.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable("id") Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @PostMapping("/events")
    public ResponseEntity<Notification> handleEvent(@RequestBody EventDto event) {
        Notification created = notificationService.processEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
