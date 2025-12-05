package com.notificationhub.service;

import com.notificationhub.model.Notification;
import com.notificationhub.model.NotificationChannel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Email Notification Service with Circuit Breaker pattern.
 * 
 * The circuit breaker protects against cascading failures when the email
 * provider is down or experiencing issues.
 * 
 * @author Shivam Srivastav
 */
@Service
public class EmailNotificationService implements NotificationChannelService {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);

    @Override
    @CircuitBreaker(name = "emailService", fallbackMethod = "sendFallback")
    public void send(Notification notification) {
        log.info("Sending EMAIL to {}: {}", notification.getRecipient(), notification.getContent());

        // Simulate email sending delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simulate random failure for circuit breaker testing
        if (Math.random() < 0.1) {
            throw new RuntimeException("Simulated EMAIL provider failure");
        }

        log.info("EMAIL sent successfully to {}", notification.getRecipient());
    }

    /**
     * Fallback method when circuit breaker is OPEN.
     * Called when the email service is unavailable.
     */
    public void sendFallback(Notification notification, Exception e) {
        log.warn("CIRCUIT BREAKER OPEN: Email service unavailable. Notification {} queued for retry. Error: {}",
                notification.getId(), e.getMessage());
        // In production, you might:
        // 1. Queue to a fallback system
        // 2. Store in database for later retry
        // 3. Send to a backup email provider
        throw new RuntimeException("Email service temporarily unavailable: " + e.getMessage());
    }

    @Override
    public boolean supports(NotificationChannel channel) {
        return NotificationChannel.EMAIL == channel;
    }
}
