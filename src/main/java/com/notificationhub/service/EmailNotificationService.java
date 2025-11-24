package com.notificationhub.service;

import com.notificationhub.model.Notification;
import com.notificationhub.model.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationChannelService {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);

    @Override
    public void send(Notification notification) {
        log.info("Sending EMAIL to {}: {}", notification.getRecipient(), notification.getContent());
        // Simulate email sending delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Simulate random failure for retry testing
        if (Math.random() < 0.1) {
            throw new RuntimeException("Simulated EMAIL provider failure");
        }
        log.info("EMAIL sent successfully to {}", notification.getRecipient());
    }

    @Override
    public boolean supports(NotificationChannel channel) {
        return NotificationChannel.EMAIL == channel;
    }
}
