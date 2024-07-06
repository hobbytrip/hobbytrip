package capstone.communityservice.domain.server.controller;

import capstone.communityservice.domain.server.dto.request.ServerUserDeleteRequest;
import capstone.communityservice.domain.server.dto.response.ServerUserUpdateResponse;
import capstone.communityservice.domain.server.dto.request.ServerUserUpdateRequest;
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
    public DataResponseDto<Object> update(@Valid @RequestBody ServerUserUpdateRequest request){
        ServerUserUpdateResponse response = serverUserCommandService.update(request);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody ServerUserDeleteRequest request){
        serverUserCommandService.delete(request);

        return DataResponseDto.of("ServerUser delete success!!");
    }
}
