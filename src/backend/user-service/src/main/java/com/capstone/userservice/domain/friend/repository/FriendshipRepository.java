package com.capstone.userservice.domain.friend.repository;

import com.capstone.userservice.domain.friend.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
}
