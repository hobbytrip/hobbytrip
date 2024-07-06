package capstone.communityservice.global.external;

import capstone.communityservice.domain.user.dto.response.UserFeignResponseDto;
import org.springframework.stereotype.Component;

/**
 * OpenFeign 등록 전에 사용할 Fake Client
 */
@Component
public class UserServiceFakeClient {

    public UserFeignResponseDto getUser(Long originalId){
        UserFeignResponseDto userFeignResponseDto = new UserFeignResponseDto();
        userFeignResponseDto.setOriginalId(originalId);
        userFeignResponseDto.setEmail("abc" + originalId + "@naver.com");
        userFeignResponseDto.setName("abc");
        userFeignResponseDto.setProfile("http://image.png");

        return userFeignResponseDto;
    }
}
