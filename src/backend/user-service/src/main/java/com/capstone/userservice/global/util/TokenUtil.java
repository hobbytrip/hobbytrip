package com.capstone.userservice.global.util;


import com.capstone.userservice.domain.user.dto.request.UserRequest;
import com.capstone.userservice.global.common.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
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
    private static final String USER_EMAIL = "email";
    private static final String BEARER_TYPE = "Bearer";
    private static final String AUTHORITIES_KEY = "auth";
    private final Key key;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;
    private final String jwtHeader;

    public TokenUtil(@Value("${jwt.secret}") String secretKey,
                     @Value("${jwt.access_token_expiration_time}") long accessTokenExpTime,
                     @Value("${jwt.refresh_token_expiration_time}") long refreshTokenExpTime,
                     @Value("jwt.header") String jwtHeader) {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
        this.refreshTokenExpTime = refreshTokenExpTime;
        this.jwtHeader = jwtHeader;
    }

    public TokenDto generateToken(UserRequest user, Authentication authentication) {

        // Authentication 객체에서 userDetails 추출
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // userDetails 객체에서 userId 가져오기
        String userId = userDetails.getUsername();

        //권한 'ROLE_USER'
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessTokenExpTime);

        /**
         * Access Token 생성
         *  payload "auth": "ROLE_USER"
         *  payload "sub": "email"
         *  payload "userId": "userId"
         *  payload "iat": 토큰 발급 시간
         *  payload "exp" : 토큰 만료 시간
         *  header "alg" : "HS256"
         */
        String accessToken = Jwts.builder()
                .claim(USER_EMAIL, user.getEmail())
                .claim(AUTHORITIES_KEY, authorities)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        /**
         * Refresh Token 생성
         */
        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(now + refreshTokenExpTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
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
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    //토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String getEmail(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims.get(USER_EMAIL, String.class);
    }

    public Long getUserId(String token) {
        Long userId = Long.valueOf(Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject());
        return userId;
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(jwtHeader, accessToken);
    }
}

