package capstone.communityservice.domain.server.controller;

import capstone.communityservice.domain.server.dto.ServerCreateRequestDto;
import capstone.communityservice.domain.server.dto.ServerInviteCodeResponse;
import capstone.communityservice.domain.server.dto.ServerJoinRequestDto;
import capstone.communityservice.domain.server.dto.ServerResponseDto;
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
            @RequestPart(name = "profile", required = false) MultipartFile profile){
        ServerResponseDto response = serverCommandService.save(requestDto, profile);
        return DataResponseDto.of(response);
    }

    @PostMapping("/join")
    public DataResponseDto<Object> join(@Valid @RequestBody ServerJoinRequestDto serverJoinRequestDto){
        ServerResponseDto response = serverCommandService.join(serverJoinRequestDto);
        return DataResponseDto.of(response);
    }

    @GetMapping("/{serverId}/invitation")
    public DataResponseDto<Object> generateInvitationCode(@PathVariable("serverId") Long serverId){
        ServerInviteCodeResponse response = serverCommandService.generatedServerInviteCode(serverId);

        return DataResponseDto.of(response);
    }

//    @DeleteMapping("/{managerId}/{serverId}")
//    public DataResponseDto<Object> remove(@PathVariable("managerId") String managerId, @PathVariable("serverid") String serverId){
//        serverCommandService.remove(managerId, serverId);
//    }
//
//    @PatchMapping("/serverName")
//    public DataResponseDto<Object> rename(@Valid @RequestBody ServerUpdateNameRequestDto requestDto){
//        serverCommandService.rename(requestDto);
//    }
//
//    @PatchMapping("/serverProfile")
//    public DataResponseDto<Object> reprofile(
//            @Valid @RequestPart(value = "requestDto") ServerUpdateProfileRequestDto requestDto,
//            @RequestPart(value = "profile") MultipartFile profile){
//        serverCommandService.reprofile(requestDto, profile);
//    }
//
}
