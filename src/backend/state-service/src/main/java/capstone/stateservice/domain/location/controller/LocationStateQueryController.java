package capstone.stateservice.domain.location.controller;

import capstone.stateservice.domain.location.dto.UserLocationDto;
import capstone.stateservice.domain.location.service.query.LocationStateQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationStateQueryController {

    private final LocationStateQueryService locationStateQueryService;

    @GetMapping("/feign/{serverId}/{userId}")
    public UserLocationDto getUserLocation(@PathVariable("serverId") Long serverId,
                                           @PathVariable("userId") Long userId) {

        return locationStateQueryService.getUserLocationState(serverId, userId);
    }
}
