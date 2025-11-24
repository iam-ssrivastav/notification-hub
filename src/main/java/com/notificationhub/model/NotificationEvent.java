package com.notificationhub.model;

import java.io.Serializable;
import java.util.Map;

public class NotificationEvent implements Serializable {
    private String recipient;
    private String subject;
    private String content;
    private String templateCode;
    private Map<String, String> variables;
    private NotificationChannel channel;
    private Long notificationId;

    public NotificationEvent() {
    }

    public NotificationEvent(String recipient, String subject, String content, String templateCode,
            Map<String, String> variables, NotificationChannel channel, Long notificationId) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.templateCode = templateCode;
        this.variables = variables;
        this.channel = channel;
        this.notificationId = notificationId;
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

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
}
