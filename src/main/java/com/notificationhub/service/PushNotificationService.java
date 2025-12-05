package com.notificationhub.service;

import com.notificationhub.model.Notification;
import com.notificationhub.model.NotificationChannel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Push Notification Service with Circuit Breaker pattern.
 * 
 * @author Shivam Srivastav
 */
@Service
public class PushNotificationService implements NotificationChannelService {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);

    @Override
    @CircuitBreaker(name = "pushService", fallbackMethod = "sendFallback")
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

    /**
     * Fallback method when circuit breaker is OPEN.
     */
    public void sendFallback(Notification notification, Exception e) {
        log.warn("CIRCUIT BREAKER OPEN: Push service unavailable. Notification {} queued for retry. Error: {}",
                notification.getId(), e.getMessage());
        throw new RuntimeException("Push service temporarily unavailable: " + e.getMessage());
    }

    @Override
    public boolean supports(NotificationChannel channel) {
        return NotificationChannel.PUSH == channel;
    }
}
