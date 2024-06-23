package com.capstone.userservice.global.config;


import com.capstone.userservice.global.exception.JwtAccessDeniedHandler;
import com.capstone.userservice.global.exception.JwtAuthenticationEntryPoint;
import com.capstone.userservice.global.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final TokenUtil tokenUtil;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer configure() { //해당 경로에선 스프링 시큐리티 기능 비활성화
        return (web -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //CSRF(공격방지기능) 설정 Disable
        http.csrf(AbstractHttpConfigurer::disable)

                .formLogin().disable() //form 기반 로그인 사용 X
                .httpBasic().disable() // 기본 인증 방식 사용 X

                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션 사용 X -> stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                // exceptionHandling 에 클래스 추가 (인증 예외 처리 설정)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                // JwtUtil  를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .apply(new JwtSecurityConfig(tokenUtil));

        return http.build();
    }
}
