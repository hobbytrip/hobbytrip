package capstone.communityservice.global.external;

import capstone.communityservice.global.external.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("state-service")
public interface StateServiceClient {

    @GetMapping("/feign/server/user/state")
    ServerUsersStateResponse getServerUsersState(
            @RequestParam Long serverId,
            @RequestParam List<Long> userIds
    );

    @GetMapping("/feign/user/connection/state")
    UserConnectionStateResponse getUsersConnectionState(@RequestParam List<Long> userIds);

    @GetMapping("/feign/{serverId}/{userId}")
    UserLocationDto getUserLocation(@PathVariable("serverId") Long serverId, @PathVariable("userId") Long userId);

}
