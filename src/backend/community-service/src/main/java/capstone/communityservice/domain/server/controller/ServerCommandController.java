package capstone.communityservice.domain.server.controller;

import capstone.communityservice.domain.server.dto.*;
import capstone.communityservice.domain.server.service.ServerCommandService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
public class ServerCommandController {

    private final ServerCommandService serverCommandService;

    @PostMapping
    public DataResponseDto<Object> create(
            @Valid @RequestPart(value = "requestDto") ServerCreateRequestDto requestDto,
            @RequestPart(name = "profile", required = false) MultipartFile profile
    ){
        ServerResponseDto response = serverCommandService.create(requestDto, profile);
        return DataResponseDto.of(response);
    }

    @PostMapping("/join")
    public DataResponseDto<Object> join(@Valid @RequestBody ServerJoinRequestDto requestDto){
        ServerResponseDto response = serverCommandService.join(requestDto);
        return DataResponseDto.of(response);
    }

    @GetMapping("/{serverId}/invitation")
    public DataResponseDto<Object> generateInvitationCode(@PathVariable("serverId") Long serverId){
        ServerInviteCodeResponse response = serverCommandService.generatedServerInviteCode(serverId);

        return DataResponseDto.of(response);
    }

    @PatchMapping
    public DataResponseDto<Object> update(
            @Valid @RequestPart("requestDto") ServerUpdateRequestDto requestDto,
            @RequestPart(name = "profile", required = false) MultipartFile file){
        ServerResponseDto response = serverCommandService.update(requestDto, file);
        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody ServerDeleteRequestDto requestDto){
        serverCommandService.delete(requestDto);
        return DataResponseDto.of("Server delete success!!");
    }
}
