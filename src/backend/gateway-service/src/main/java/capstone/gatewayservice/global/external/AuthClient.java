package capstone.gatewayservice.global.external;

import org.springframework.stereotype.Component;

@Component
public interface AuthClient {
    boolean isValidToken(String accessToken);
}
