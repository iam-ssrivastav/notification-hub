package com.notificationhub.repository;

import com.notificationhub.model.Notification;
import com.notificationhub.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByRecipient(String recipient);
}
