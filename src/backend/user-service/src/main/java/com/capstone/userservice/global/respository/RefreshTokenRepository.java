package com.capstone.userservice.global.respository;

import com.capstone.userservice.global.common.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByKey(String key);
}
