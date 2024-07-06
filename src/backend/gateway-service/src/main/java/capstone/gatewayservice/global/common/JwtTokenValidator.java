package capstone.gatewayservice.global.common;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;

/**
 * JWT 토큰을 생성, 토큰 복호화 및 정보 추출, 토큰 유효성 검증 클래스
 * jwt.secret는 토큰의 암호화, 복호화를 위한 secret key로 HS256 알고리즘 사용을 위해, 256비트보다 커야함.
 * 한 단어에 1바이트이므로, 32글자 이상 설정.
 */
@Slf4j
@Component
public class JwtTokenValidator {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    @PostConstruct
    public void keyInit(){
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    // 토큰 정보 검증
    public boolean validateToken(String token){
        try{
            parseClaims(token);
        } catch(MalformedJwtException e){
            log.info("Invalid JWT token");
            log.trace("Invalid JWT token trace = {}", e);
            return false;
        } catch (ExpiredJwtException e){
            log.info("Expired JWT token");
            log.trace("Expired JWT token trace = {}", e);
            return false;
        } catch (UnsupportedJwtException e){
            log.info("Unsupported JWT token");
            log.trace("Unsupported JWT token trace = {}", e);
            return false;
        } catch(IllegalArgumentException e){
            log.info("JWT claims string is empty");
            log.trace("JWT claims string is empty trace = {}", e);
            return false;
        } catch(SecurityException e){
            log.info("Security Error");
            log.trace("Security Error = {}", e);
            return false;
        }
        return true;
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Request Header의 Access Token 정보 추출
    public String resolveAccessToken(ServerHttpRequest request){
        HttpHeaders headers = request.getHeaders();
        headers.forEach((key, value) -> {
            System.out.println(key + ": " + String.join(", ", value));
        });

        String bearerToken = request.getHeaders().getFirst(AUTHORIZATION_HEADER);

//        System.out.println(bearerToken + " " + AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }

        return null;
    }

    // Request Header에 Refresh Token 정보 추출
    public String resolveRefreshToken(ServerHttpRequest request){
        String refreshToken = request.getHeaders().getFirst(REFRESH_HEADER);
        if(StringUtils.hasText(refreshToken)){
            return refreshToken;
        }

        return null;
    }

}
