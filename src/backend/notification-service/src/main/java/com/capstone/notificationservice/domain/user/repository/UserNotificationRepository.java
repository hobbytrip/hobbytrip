package com.capstone.notificationservice.domain.user.repository;

import com.capstone.notificationservice.domain.user.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

}
