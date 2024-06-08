package com.capstone.userservice.domain.friend.repository;

import com.capstone.userservice.domain.friend.entity.Friendship;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByuserEmail(String email);
}
