package com.capstone.userservice.global.config;


import com.capstone.userservice.global.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenUtil tokenUtil;
    private final CorsFilter corsFilter;
    
}
