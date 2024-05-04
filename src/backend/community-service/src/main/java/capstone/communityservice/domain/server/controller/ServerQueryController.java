package capstone.communityservice.domain.server.controller;

import capstone.communityservice.domain.server.dto.ServerInviteCodeResponse;
import capstone.communityservice.domain.server.dto.ServerReadResponseDto;
import capstone.communityservice.domain.server.dto.ServerResponseDto;
import capstone.communityservice.domain.server.service.ServerQueryService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import capstone.communityservice.global.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
public class ServerQueryController {
    private final ServerQueryService serverQueryService;

    @GetMapping("/{serverId}/{userId}")
    public DataResponseDto<Object> read(@PathVariable("serverId") Long serverId, @PathVariable("userId") Long userId){
        ServerReadResponseDto response = serverQueryService.read(serverId, userId);

        return DataResponseDto.of(response);
    }

    @GetMapping("/open")
    public DataResponseDto<Object> search(){
        List<ServerResponseDto> response = serverQueryService.search();

        return DataResponseDto.of(response);
    }

    @GetMapping("/open/search")
    public DataResponseDto<Object> searchCondition(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                                                   @RequestParam("name") String name){
        PageResponseDto response = serverQueryService.searchCondition(name, pageNo);

        return DataResponseDto.of(response);
    }

}
