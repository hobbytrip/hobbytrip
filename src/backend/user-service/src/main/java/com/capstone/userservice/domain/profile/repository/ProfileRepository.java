package com.capstone.userservice.domain.profile.repository;

import com.capstone.userservice.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);
}
