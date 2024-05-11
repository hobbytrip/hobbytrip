package capstone.communityservice.global.external;

import capstone.communityservice.global.external.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("state-service")
public interface StateServiceClient {

    @PostMapping("/server")
    ServerUserStateResponseDto checkServerOnOff(@RequestBody ServerUserStateRequestDto requestDto);

    @PostMapping("/dm")
    DmUserStateResponseDto checkDmOnOff(@RequestBody DmUserStateRequestDto requestDto);

    @GetMapping("/{userId}")
    ServerUserLocDto userLocation(@PathVariable("userId") Long userId);

}
