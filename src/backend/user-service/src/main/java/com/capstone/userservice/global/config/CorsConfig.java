package com.capstone.userservice.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //Json -> javascript 처리 해주는 설정
        config.addAllowedOrigin("*"); //모든 출처 허용
        config.addAllowedHeader("*"); //모든 헤더 허용
        config.addAllowedMethod("*"); // Rest 허용

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
