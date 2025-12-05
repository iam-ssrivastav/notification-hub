package com.notificationhub.service;

import com.notificationhub.model.Notification;
import com.notificationhub.model.NotificationChannel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * SMS Notification Service with Circuit Breaker pattern.
 * 
 * @author Shivam Srivastav
 */
@Service
public class SmsNotificationService implements NotificationChannelService {

    private static final Logger log = LoggerFactory.getLogger(SmsNotificationService.class);

    @Override
    @CircuitBreaker(name = "smsService", fallbackMethod = "sendFallback")
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

    /**
     * Fallback method when circuit breaker is OPEN.
     */
    public void sendFallback(Notification notification, Exception e) {
        log.warn("CIRCUIT BREAKER OPEN: SMS service unavailable. Notification {} queued for retry. Error: {}",
                notification.getId(), e.getMessage());
        throw new RuntimeException("SMS service temporarily unavailable: " + e.getMessage());
    }

    @Override
    public boolean supports(NotificationChannel channel) {
        return NotificationChannel.SMS == channel;
    }
}
