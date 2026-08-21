package com.microwatch.notification.service;

import com.microwatch.notification.dto.EventDto;
import com.microwatch.notification.model.Notification;
import com.microwatch.notification.repository.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }

    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification with ID " + id + " was not found"));
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }

    public Notification processEvent(EventDto event) {
        String title;
        String message;
        String notificationType;

        if ("DOWN".equalsIgnoreCase(event.getStatus())) {
            title = "MicroWatch Alert";
            message = String.format("%s is DOWN", event.getService());
            notificationType = "SERVICE_DOWN";
        } else if ("UP".equalsIgnoreCase(event.getStatus())) {
            title = "MicroWatch Recovery";
            message = String.format("%s is UP again.", event.getService());
            notificationType = "SERVICE_RECOVERED";
        } else {
            title = "System Alert";
            message = String.format("%s status update: %s", event.getService(), event.getStatus());
            notificationType = "SYSTEM_ALERT";
        }

        Notification notification = new Notification(
                event.getServiceId(),
                title,
                message,
                notificationType
        );

        return notificationRepository.save(notification);
    }
}
