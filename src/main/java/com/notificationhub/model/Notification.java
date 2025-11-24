package com.notificationhub.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private int retryCount;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Notification() {
    }

    public Notification(Long id, String recipient, String subject, String content, NotificationChannel channel,
            NotificationStatus status, int retryCount, String errorMessage, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.channel = channel;
        this.status = status;
        this.retryCount = retryCount;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    public static class NotificationBuilder {
        private Long id;
        private String recipient;
        private String subject;
        private String content;
        private NotificationChannel channel;
        private NotificationStatus status;
        private int retryCount;
        private String errorMessage;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        NotificationBuilder() {
        }

        public NotificationBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public NotificationBuilder recipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public NotificationBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public NotificationBuilder content(String content) {
            this.content = content;
            return this;
        }

        public NotificationBuilder channel(NotificationChannel channel) {
            this.channel = channel;
            return this;
        }

        public NotificationBuilder status(NotificationStatus status) {
            this.status = status;
            return this;
        }

        public NotificationBuilder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public NotificationBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public NotificationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public NotificationBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Notification build() {
            return new Notification(id, recipient, subject, content, channel, status, retryCount, errorMessage,
                    createdAt, updatedAt);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
