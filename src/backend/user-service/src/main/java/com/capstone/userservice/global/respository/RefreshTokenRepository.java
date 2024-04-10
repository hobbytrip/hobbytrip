package com.capstone.userservice.global.respository;

import com.capstone.userservice.global.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key);
}
