package com.capstone.userservice.domain.profile.service;

import com.capstone.userservice.domain.user.dto.response.UserFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/feign/profile/{userId}")
    UserFeignResponse getUser(@PathVariable("userId") Long userId);
}
