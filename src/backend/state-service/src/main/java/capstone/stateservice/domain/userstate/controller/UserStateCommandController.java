package capstone.stateservice.domain.userstate.controller;

import capstone.stateservice.domain.userstate.service.command.UserStateCommandService;
import capstone.stateservice.global.common.dto.DataResponseDto;
import capstone.stateservice.infra.kafka.consumer.state.dto.ConnectionStateInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserStateCommandController {

    private final UserStateCommandService userStateCommandService;

    @PostMapping("/connection/info")
    DataResponseDto<Long> saveUserConnectionState(@RequestBody ConnectionStateInfo connectionStateInfo) {

        return userStateCommandService.saveUserConnectionState(connectionStateInfo);
    }
}
