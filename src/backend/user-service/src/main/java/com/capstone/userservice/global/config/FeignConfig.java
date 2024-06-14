package com.capstone.userservice.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.capstone.userservice.domain.friend.service")
public class FeignConfig {
    
}
