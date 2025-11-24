package com.notificationhub.service;

import com.notificationhub.model.Notification;
import com.notificationhub.model.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService implements NotificationChannelService {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);

    @Override
    public void send(Notification notification) {
        log.info("Sending PUSH to {}: {}", notification.getRecipient(), notification.getContent());
        // Simulate Push notification
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("PUSH sent successfully to {}", notification.getRecipient());
    }

    @Override
    public boolean supports(NotificationChannel channel) {
        return NotificationChannel.PUSH == channel;
    }
}
