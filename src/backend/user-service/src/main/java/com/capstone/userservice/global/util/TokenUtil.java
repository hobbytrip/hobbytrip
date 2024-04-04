package com.capstone.userservice.global.util;


import com.capstone.userservice.domain.user.dto.CustomUserInfoDto;
import com.capstone.userservice.global.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenUtil {
    private static final String USER_ID = "userId";
    private static final String BEARER_TYPE = "Bearer";
    private static final String AUTHORITIES_KEY = "auth";
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

    public JwtTokenDto generateToken(CustomUserInfoDto user, Authentication authentication) {

        //권한 'ROLE_USER'
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessTokenExpTime);

        /**
         * Access Token 생성
         *  payload "sub": "email"
         *  payload "userId": "userId"
         *  payload "iat": 토큰 발급 시간
         *  payload "exp"
         *  header "alg" : "HS512"
         */
        String accessToken = Jwts.builder()
                .claim(USER_ID, user.getUserId())
                .claim(AUTHORITIES_KEY, authorities)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.ES512)
                .compact();

        /**
         * Refresh Token 생성
         */
        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
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
     * Token 에서 유저 권한 추출 -> authentication 에 저장
     *
     * @param accessToken
     * @return authentication
     */
    public Authentication getAuthentication(String accessToken) {
        //토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        //클레임에서 권한 정보 가져옴
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        //UserDetails 객체로 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
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

