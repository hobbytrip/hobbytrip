package com.capstone.notificationservice.domain.user.service;

import com.capstone.notificationservice.domain.user.entity.User;
import com.capstone.notificationservice.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Optional<User> findUser(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User findUser(String Email) {
        return userRepository.findByEmail(Email);
    }
}
