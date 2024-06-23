package capstone.chatservice.global.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenHandler {

    @Value("${jwt.secret}")
    private String secretKey;

    public static final String BEARER_PREFIX = "Bearer ";

    public boolean validateToken(String token) {
        String jwtToken = parseBearerToken(token);
        try {
            parseClaims(jwtToken);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token");
            log.trace("Invalid JWT token trace = {}", e);
            return false;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
            log.trace("Expired JWT token trace = {}", e);
            return false;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
            log.trace("Unsupported JWT token trace = {}", e);
            return false;
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty");
            log.trace("JWT claims string is empty trace = {}", e);
            return false;
        } catch (SecurityException e) {
            log.info("Security Error");
            log.trace("Security Error = {}", e);
            return false;
        }
        return true;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String parseBearerToken(String token) {
        if (token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        return null;
    }
}