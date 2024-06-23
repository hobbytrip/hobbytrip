package com.capstone.notificationservice.domain.user.service;

import com.capstone.notificationservice.domain.user.entity.User;
import com.capstone.notificationservice.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Optional<User> findUser(Long userId) {
        log.info("userId0 :{}",userRepository.findById(userId));
        return userRepository.findById(userId);
    }

    @Transactional
    public User findUser(String Email) {
        return userRepository.findByEmail(Email);
    }
}
