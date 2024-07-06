package capstone.communityservice.domain.user.controller;

import capstone.communityservice.domain.user.dto.request.UserCreateRequest;
import capstone.communityservice.domain.user.dto.response.UserResponse;
import capstone.communityservice.domain.user.service.UserCommandService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping
    public DataResponseDto<Object> create(@RequestBody UserCreateRequest request){
        UserResponse response = userCommandService.save(request);
        return DataResponseDto.of(response);
    }
}
