package com.capstone.notificationservice.domain.user.repository;

import com.capstone.notificationservice.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);
    User findByEmail(String email);
}
