package com.capstone.userservice.global.util;


import com.capstone.userservice.domain.user.dto.CustomUserInfoDto;
import com.capstone.userservice.global.dto.JwtTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenUtil {
    private static final String USER_ID = "userId";
    private static final String BEARER_TYPE = "Bearer";
    private final Key key;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;

    public TokenUtil(@Value("${jwt.secret}") String secretKey,
                     @Value("${jwt.access_token_expiration_time}") long accessTokenExpTime,
                     @Value("${jwt.refresh_token_expiration_time}") long refreshTokenExpTime) {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    public JwtTokenDto generateToken(CustomUserInfoDto user) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessTokenExpTime);

        /**
         * Access Token 생성
         *  payload "sub": "email"
         *  payload "userId": "userId"
         *  payload "exp"
         *  header "alg" : "HS512"
         */
        String accessToken = Jwts.builder()
                .setSubject(user.getEmail())
                .claim(USER_ID, user.getUserId())
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.ES512)
                .compact();

        /**
         * Refresh Token 생성
         */
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshTokenExpTime))
                .signWith(key, SignatureAlgorithm.ES512)
                .compact();

        return JwtTokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Token 에서 UserEmail 추출
     *
     * @param accessToken
     * @return userEmail
     */
    public String getUserEmail(String accessToken) {
        return parseClaims(accessToken).getSubject();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰입니다.");
        }
        return false;
    }
}

