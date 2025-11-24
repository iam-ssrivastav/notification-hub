package com.notificationhub.event;

import com.notificationhub.model.NotificationEvent;
import com.notificationhub.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventListener.class);

    private final NotificationService notificationService;

    public NotificationEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 1000, multiplier = 2.0), autoCreateTopics = "true", include = {
            RuntimeException.class })
    @KafkaListener(topics = "${app.kafka.topic.notification-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleNotificationEvent(NotificationEvent event, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received event from topic {}: {}", topic, event);
        notificationService.processNotification(event);
    }

    @DltHandler
    public void handleDlt(NotificationEvent event, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error("Event moved to DLQ from topic {}: {}", topic, event);
        // Here you could update the DB status to DLQ or alert admin
    }
}
