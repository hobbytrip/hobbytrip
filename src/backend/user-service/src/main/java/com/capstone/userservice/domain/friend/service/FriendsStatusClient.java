package com.capstone.userservice.domain.friend.service;

import com.capstone.userservice.domain.friend.dto.response.FriendsStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "state-service")
public interface FriendsStatusClient {

    @GetMapping("/friends/{userId}")
    FriendsStatusResponse getFriendStatus(@PathVariable("userId") Long userId);
}
