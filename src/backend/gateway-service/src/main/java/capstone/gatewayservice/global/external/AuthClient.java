package capstone.gatewayservice.global.external;

import capstone.gatewayservice.global.common.dto.DataResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface AuthClient {
    DataResponseDto isValidToken(String accessToken);
}
