package com.notificationhub.service;

import com.notificationhub.model.Notification;
import com.notificationhub.model.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService implements NotificationChannelService {

    private static final Logger log = LoggerFactory.getLogger(SmsNotificationService.class);

    @Override
    public void send(Notification notification) {
        log.info("Sending SMS to {}: {}", notification.getRecipient(), notification.getContent());
        // Simulate SMS sending
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("SMS sent successfully to {}", notification.getRecipient());
    }

    @Override
    public boolean supports(NotificationChannel channel) {
        return NotificationChannel.SMS == channel;
    }
}
