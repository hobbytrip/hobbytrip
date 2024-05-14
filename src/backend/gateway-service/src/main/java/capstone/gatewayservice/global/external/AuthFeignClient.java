package capstone.gatewayservice.global.external;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
@Qualifier("AuthFeignClient")
public interface AuthFeignClient extends AuthClient{

    @Override
    @PostMapping("/isLogin")
    boolean isValidToken(String accessToken);
}
