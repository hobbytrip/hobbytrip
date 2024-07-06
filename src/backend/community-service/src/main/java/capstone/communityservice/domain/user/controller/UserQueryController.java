package capstone.communityservice.domain.user.controller;

import capstone.communityservice.domain.user.dto.response.UserReadResponse;
import capstone.communityservice.domain.user.dto.response.UserServerDmInfo;
import capstone.communityservice.domain.user.service.UserQueryService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/{userId}")
    public DataResponseDto<Object> read(@PathVariable("userId") Long userId){
        UserReadResponse response = userQueryService.read(userId);

        return DataResponseDto.of(response);
    }

    @GetMapping("/feign/{userId}")
    public UserServerDmInfo feignRead(@PathVariable("userId") Long userId){
        UserServerDmInfo response = userQueryService.feignRead(userId);

        return response;
    }
}
