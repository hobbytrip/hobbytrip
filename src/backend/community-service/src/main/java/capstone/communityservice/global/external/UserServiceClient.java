package capstone.communityservice.global.external;

import capstone.communityservice.domain.user.dto.UserFeignResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/{email}")
    UserFeignResponseDto getUser(@PathVariable("email") String email);
}
