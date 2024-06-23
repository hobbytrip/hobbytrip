package com.capstone.userservice.global.config;

import com.capstone.userservice.global.common.filter.JwtFilter;
import com.capstone.userservice.global.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//SecurityConfig 에 적용
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenUtil tokenUtil;

    // tokenUtil 를 주입받아서 JwtFilter 를 통해 Security 로직에 필터 등록
    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenUtil);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
