package com.capstone.userservice.domain.friend.service;

import com.capstone.userservice.domain.friend.dto.response.UserConnectionStateResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "state-service")
public interface FriendsStatusClient {

    @GetMapping("/feign/user/connection/state")
    UserConnectionStateResponse getFriendStatus(@RequestParam("userIds") List<Long> userIds);
}
