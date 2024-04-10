package com.capstone.userservice.global.common.filter;

import com.capstone.userservice.global.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer";

    private final TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //request header 에서 토큰 꺼냄
        String jwt = resolveToken(request);

        //토큰 유효성 검사, 정상 토큰이면 Authentication 을 가져와서 SecurityContext 에 저장
        if (StringUtils.hasText(jwt) && tokenUtil.validateToken(jwt)) {
            // 사용자 이메일을 기반으로 Authentication 객체 생성
            Authentication authentication = tokenUtil.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.split(" ")[1].trim(); //공백 제거
        }
        return null;
    }
}
