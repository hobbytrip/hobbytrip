package com.capstone.userservice.domain.user.controller;


import com.capstone.userservice.domain.user.dto.request.TokenRequest;
import com.capstone.userservice.domain.user.dto.request.UserRequest;
import com.capstone.userservice.domain.user.service.AuthService;
import com.capstone.userservice.global.common.dto.DataResponseDto;
import com.capstone.userservice.global.common.dto.TokenDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/reissue")
    public DataResponseDto<Object> reissue(@RequestBody TokenRequest tokenRequest,
                                           UserRequest userRequest,
                                           HttpServletResponse response) {
        TokenDto tokenDto = authService.reissue(tokenRequest, userRequest, response);

        return DataResponseDto.of(tokenDto);

    }

    //true 시 로그인 되어있는 상태
    @PostMapping("/isLogin")
    public DataResponseDto<Object> isLogin(@RequestBody String accessToken) {
        Boolean response = authService.isLogin(accessToken);
        return DataResponseDto.of(response);
    }
}
