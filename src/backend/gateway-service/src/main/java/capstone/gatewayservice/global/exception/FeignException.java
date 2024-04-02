package capstone.gatewayservice.global.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignException implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 404:
                if(methodKey.contains("isLogin")){
                    log.error("FeignClient Error: 사용자는 로그인 되어있지 않습니다.");
                    return new GlobalException("사용자는 로그인 되어있지 않습니다.");
                }
        }

        return null;
    }
}
