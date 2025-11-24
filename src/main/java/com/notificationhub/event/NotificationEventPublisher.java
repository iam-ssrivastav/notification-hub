package com.notificationhub.event;

import com.notificationhub.model.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventPublisher.class);

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public NotificationEventPublisher(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${app.kafka.topic.notification-events}")
    private String topicName;

    public void publishNotificationEvent(NotificationEvent event) {
        log.info("Publishing notification event to topic: {} for recipient: {}", topicName, event.getRecipient());
        kafkaTemplate.send(topicName, event);
    }
}
