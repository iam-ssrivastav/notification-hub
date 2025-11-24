package com.notificationhub.controller;

import com.notificationhub.model.Notification;
import com.notificationhub.model.NotificationEvent;
import com.notificationhub.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationEvent event) {
        Notification notification = notificationService.createNotification(event);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotification(id));
    }
}
