package capstone.communityservice.domain.server.controller;

import capstone.communityservice.domain.server.dto.ServerInviteCodeResponse;
import capstone.communityservice.domain.server.service.ServerQueryService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
public class ServerQueryController {
    private final ServerQueryService serverQueryService;
//    @GetMapping("/{userId}")
//    public DataResponseDto<Object> get(@PathVariable("userId") String userId){
//        serverCommandService.findServerByUserId();
//    }

}
