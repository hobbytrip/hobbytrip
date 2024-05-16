package com.capstone.notificationservice.domain.dm.respository;

import com.capstone.notificationservice.domain.dm.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
