package com.notificationhub.service;

import com.notificationhub.model.Notification;

public interface NotificationChannelService {
    void send(Notification notification);

    boolean supports(com.notificationhub.model.NotificationChannel channel);
}
