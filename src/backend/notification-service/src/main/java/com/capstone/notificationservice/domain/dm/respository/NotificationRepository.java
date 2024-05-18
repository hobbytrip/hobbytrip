package com.capstone.notificationservice.domain.dm.respository;

import com.capstone.notificationservice.domain.dm.entity.DmNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<DmNotification, Long> {
}
