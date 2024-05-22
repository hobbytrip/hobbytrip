package com.capstone.notificationservice.domain.server.repository;

import com.capstone.notificationservice.domain.server.entity.ServerNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<ServerNotification, Long> {
}
