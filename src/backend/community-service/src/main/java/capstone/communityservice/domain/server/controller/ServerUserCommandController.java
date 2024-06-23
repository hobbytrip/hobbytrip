package capstone.communityservice.domain.server.controller;

import capstone.communityservice.domain.server.dto.ServerUserDeleteRequestDto;
import capstone.communityservice.domain.server.dto.ServerUserResponseDto;
import capstone.communityservice.domain.server.dto.ServerUserUpdateDto;
import capstone.communityservice.domain.server.service.ServerUserCommandService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/serverUser")
public class ServerUserCommandController {

    private final ServerUserCommandService serverUserCommandService;

    @PatchMapping
    public DataResponseDto<Object> update(@Valid @RequestBody ServerUserUpdateDto requestDto){
        ServerUserResponseDto response = serverUserCommandService.update(requestDto);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody ServerUserDeleteRequestDto requestDto){
        serverUserCommandService.delete(requestDto);

        return DataResponseDto.of("ServerUser delete success!!");
    }
}
