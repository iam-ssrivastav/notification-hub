package com.notificationhub.service;

import com.notificationhub.event.NotificationEventPublisher;
import com.notificationhub.model.*;
import com.notificationhub.repository.NotificationRepository;
import com.notificationhub.repository.TemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final TemplateRepository templateRepository;
    private final NotificationEventPublisher eventPublisher;
    private final List<NotificationChannelService> channelServices;

    public NotificationService(NotificationRepository notificationRepository, TemplateRepository templateRepository,
            NotificationEventPublisher eventPublisher, List<NotificationChannelService> channelServices) {
        this.notificationRepository = notificationRepository;
        this.templateRepository = templateRepository;
        this.eventPublisher = eventPublisher;
        this.channelServices = channelServices;
    }

    @Transactional
    public Notification createNotification(NotificationEvent event) {
        String content = event.getContent();
        String subject = event.getSubject();

        // Handle Template
        if (event.getTemplateCode() != null) {
            Template template = templateRepository.findByCode(event.getTemplateCode())
                    .orElseThrow(() -> new RuntimeException("Template not found: " + event.getTemplateCode()));

            content = processTemplate(template.getContentTemplate(), event.getVariables());
            subject = processTemplate(template.getSubjectTemplate(), event.getVariables());

            // Override channel if not specified in event but present in template
            if (event.getChannel() == null) {
                event.setChannel(template.getChannel());
            }
        }

        Notification notification = Notification.builder()
                .recipient(event.getRecipient())
                .subject(subject)
                .content(content)
                .channel(event.getChannel())
                .status(NotificationStatus.PENDING)
                .retryCount(0)
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        // Enrich event with ID for tracking
        event.setNotificationId(savedNotification.getId());
        event.setContent(content);
        event.setSubject(subject);

        // Publish to Kafka
        eventPublisher.publishNotificationEvent(event);

        return savedNotification;
    }

    public void processNotification(NotificationEvent event) {
        Notification notification = notificationRepository.findById(event.getNotificationId())
                .orElseThrow(() -> new RuntimeException("Notification not found: " + event.getNotificationId()));

        try {
            NotificationChannelService service = channelServices.stream()
                    .filter(s -> s.supports(notification.getChannel()))
                    .findFirst()
                    .orElseThrow(
                            () -> new RuntimeException("No service found for channel: " + notification.getChannel()));

            service.send(notification);

            notification.setStatus(NotificationStatus.SENT);
            notificationRepository.save(notification);

        } catch (Exception e) {
            log.error("Failed to send notification: {}", e.getMessage());
            handleFailure(notification, e.getMessage());
            throw e; // Rethrow to trigger Kafka retry/DLQ
        }
    }

    private void handleFailure(Notification notification, String error) {
        notification.setStatus(NotificationStatus.FAILED);
        notification.setErrorMessage(error);
        notification.setRetryCount(notification.getRetryCount() + 1);
        notificationRepository.save(notification);
    }

    private String processTemplate(String template, Map<String, String> variables) {
        if (template == null)
            return null;
        if (variables == null)
            return template;

        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }

    public Notification getNotification(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }
}
