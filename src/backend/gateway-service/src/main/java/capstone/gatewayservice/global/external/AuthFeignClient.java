package capstone.gatewayservice.global.external;

import capstone.gatewayservice.global.common.config.FeignConfiguration;
import capstone.gatewayservice.global.common.dto.DataResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service", configuration = FeignConfiguration.class)
@Qualifier("AuthFeignClient")
public interface AuthFeignClient extends AuthClient{

    @Override
    @PostMapping("/isLogin")
    DataResponseDto isValidToken(String accessToken);
}
