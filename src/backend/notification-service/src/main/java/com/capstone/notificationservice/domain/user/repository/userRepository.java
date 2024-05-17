package com.capstone.notificationservice.domain.user.repository;

import com.capstone.notificationservice.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User, Long> {
}
