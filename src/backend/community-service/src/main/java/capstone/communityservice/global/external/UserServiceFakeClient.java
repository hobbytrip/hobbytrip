package capstone.communityservice.global.external;

import capstone.communityservice.domain.user.dto.UserFeignResponseDto;
import org.springframework.stereotype.Component;

/**
 * OpenFeign 등록 전에 사용할 Fake Client
 */
@Component
public class UserServiceFakeClient {

    public UserFeignResponseDto getUser(){
        UserFeignResponseDto userFeignResponseDto = new UserFeignResponseDto();
        userFeignResponseDto.setOriginalId(1L);
        userFeignResponseDto.setEmail("abc@naver.com");
        userFeignResponseDto.setName("abc");
        userFeignResponseDto.setProfile("http://image.png");

        return userFeignResponseDto;
    }
}
