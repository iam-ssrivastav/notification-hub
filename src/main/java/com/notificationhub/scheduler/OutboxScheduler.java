package com.notificationhub.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationhub.model.NotificationEvent;
import com.notificationhub.model.OutboxEvent;
import com.notificationhub.repository.OutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Outbox Scheduler - Polling Publisher Pattern.
 * 
 * Polls the outbox_events table and publishes events to Kafka.
 * This ensures reliable event delivery even if Kafka is temporarily
 * unavailable.
 * 
 * @author Shivam Srivastav
 */
@Component
public class OutboxScheduler {

    private static final Logger log = LoggerFactory.getLogger(OutboxScheduler.class);

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OutboxScheduler(OutboxRepository outboxRepository,
            KafkaTemplate<String, NotificationEvent> kafkaTemplate,
            ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelay = 5000) // Poll every 5 seconds
    @Transactional
    public void processOutboxEvents() {
        List<OutboxEvent> events = outboxRepository.findAll();

        if (!events.isEmpty()) {
            log.info("Found {} outbox events to process", events.size());
        }

        for (OutboxEvent event : events) {
            try {
                log.info("Publishing event: {} to topic: {}", event.getEventType(), event.getTopic());

                // Deserialize payload to NotificationEvent
                NotificationEvent notificationEvent = objectMapper.readValue(
                        event.getPayload(), NotificationEvent.class);

                // Publish to Kafka
                kafkaTemplate.send(event.getTopic(), notificationEvent);

                // Delete after successful publication
                outboxRepository.delete(event);

                log.info("Successfully published and removed outbox event: {}", event.getId());

            } catch (Exception e) {
                log.error("Failed to process outbox event: {}", event.getId(), e);
                // Event stays in outbox for retry on next poll
            }
        }
    }
}
