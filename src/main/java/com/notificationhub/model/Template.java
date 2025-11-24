package com.notificationhub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // e.g., WELCOME_EMAIL

    private String subjectTemplate; // e.g., Welcome, {name}!

    @Column(columnDefinition = "TEXT")
    private String contentTemplate; // e.g., Hello {name}, welcome to...

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    public Template() {
    }

    public Template(Long id, String code, String subjectTemplate, String contentTemplate, NotificationChannel channel) {
        this.id = id;
        this.code = code;
        this.subjectTemplate = subjectTemplate;
        this.contentTemplate = contentTemplate;
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubjectTemplate() {
        return subjectTemplate;
    }

    public void setSubjectTemplate(String subjectTemplate) {
        this.subjectTemplate = subjectTemplate;
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(String contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }
}
