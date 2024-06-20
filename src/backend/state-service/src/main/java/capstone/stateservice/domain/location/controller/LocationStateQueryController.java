package capstone.stateservice.domain.location.controller;

import capstone.stateservice.domain.location.dto.UserLocationDto;
import capstone.stateservice.domain.location.service.query.LocationStateQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LocationStateQueryController {

    private final LocationStateQueryService locationStateQueryService;

    @GetMapping("/feign/{serverId}/{userId}")
    public UserLocationDto getUserLocation(@PathVariable("serverId") Long serverId,
                                           @PathVariable("userId") Long userId) {

        UserLocationDto userLocationState = locationStateQueryService.getUserLocationState(serverId, userId);
        Long channelId = userLocationState.getChannelId();
        log.info("######### 컨트롤러에서 반환하는 값 ###########");
        log.info("userId {}", userId);
        log.info("serverId {}", serverId);
        log.info("channelId {}", channelId);
        log.info("##################");
        return userLocationState;
    }
}
