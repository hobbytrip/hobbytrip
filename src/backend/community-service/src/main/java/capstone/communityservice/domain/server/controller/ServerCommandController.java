package capstone.communityservice.domain.server.controller;

import capstone.communityservice.domain.server.dto.request.*;
import capstone.communityservice.domain.server.dto.response.ServerInviteCodeResponse;
import capstone.communityservice.domain.server.dto.response.ServerResponse;
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
            @Valid @RequestPart(value = "requestDto") ServerCreateRequest request,
            @RequestPart(name = "profile", required = false) MultipartFile profile
    ){
        ServerResponse response = serverCommandService.create(request, profile);

        return DataResponseDto.of(response);
    }

    @PostMapping("/join")
    public DataResponseDto<Object> join(@Valid @RequestBody ServerJoinRequest request){
        ServerResponse response = serverCommandService.join(request);

        return DataResponseDto.of(response);
    }

    @GetMapping("/{serverId}/invitation")
    public DataResponseDto<Object> generateInvitationCode(@PathVariable("serverId") Long serverId){
        ServerInviteCodeResponse response = serverCommandService.generatedServerInviteCode(serverId);

        return DataResponseDto.of(response);
    }

    @PatchMapping
    public DataResponseDto<Object> update(
            @Valid @RequestPart("requestDto") ServerUpdateRequest request,
            @RequestPart(name = "profile", required = false) MultipartFile file){
        ServerResponse response = serverCommandService.update(request, file);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody ServerDeleteRequest request){
        serverCommandService.delete(request);

        return DataResponseDto.of("Server delete success!!");
    }

    @PatchMapping("/profile")
    public DataResponseDto<Object> deleteProfile(@Valid @RequestBody ServerProfileDeleteRequest request){
        ServerResponse response = serverCommandService.deleteProfile(request);

        return DataResponseDto.of(response);
    }
}
