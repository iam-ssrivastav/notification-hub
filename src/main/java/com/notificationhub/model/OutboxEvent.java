package com.notificationhub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Outbox Event entity for the Transactional Outbox Pattern.
 * Events are stored in this table within the same transaction as the business
 * logic,
 * then asynchronously published to Kafka by a scheduled task.
 * 
 * @author Shivam Srivastav
 */
@Entity
@Table(name = "outbox_events")
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType; // e.g., "NOTIFICATION"
    private String aggregateId; // e.g., Notification ID
    private String eventType; // e.g., "NOTIFICATION_CREATED"

    @Column(length = 4096)
    private String payload; // JSON payload

    private String topic; // Kafka topic

    private LocalDateTime createdAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final OutboxEvent event = new OutboxEvent();

        public Builder aggregateType(String aggregateType) {
            event.aggregateType = aggregateType;
            return this;
        }

        public Builder aggregateId(String aggregateId) {
            event.aggregateId = aggregateId;
            return this;
        }

        public Builder eventType(String eventType) {
            event.eventType = eventType;
            return this;
        }

        public Builder payload(String payload) {
            event.payload = payload;
            return this;
        }

        public Builder topic(String topic) {
            event.topic = topic;
            return this;
        }

        public OutboxEvent build() {
            return event;
        }
    }
}
