package capstone.communityservice.global.external;

import capstone.communityservice.global.external.dto.ServerUserLocDto;
import capstone.communityservice.global.external.dto.ServerUserStateRequestDto;
import capstone.communityservice.global.external.dto.ServerUserStateResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("state-service")
public interface StateServiceClient {

    @PostMapping("/")
    ServerUserStateResponseDto checkOnOff(@RequestBody ServerUserStateRequestDto requestDto);

    @GetMapping("/{userId}")
    ServerUserLocDto userLocation(@PathVariable("userId") Long userId);

}
