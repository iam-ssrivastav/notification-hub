package com.notificationhub.repository;

import com.notificationhub.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Outbox Events.
 * Used by the OutboxScheduler to poll and publish events.
 * 
 * @author Shivam Srivastav
 */
@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
}
