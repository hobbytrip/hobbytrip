package com.capstone.userservice.domain.user.controller;


import com.capstone.userservice.domain.user.dto.TokenRequest;
import com.capstone.userservice.domain.user.dto.UserRequest;
import com.capstone.userservice.domain.user.service.AuthService;
import com.capstone.userservice.global.common.dto.DataResponseDto;
import com.capstone.userservice.global.common.dto.TokenDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/reissue")
    public DataResponseDto<Object> reissue(@Valid @RequestBody TokenRequest tokenRequest,
                                           UserRequest userRequest,
                                           HttpServletResponse response) {
        TokenDto tokenDto = authService.reissue(tokenRequest, userRequest, response);

        return DataResponseDto.of(tokenDto);

    }

    //true 시 로그인 되어있는 상태
    @PostMapping("/loginCheck")
    public DataResponseDto<Object> loginCheck(@RequestBody String accessToken) {
        Boolean response = authService.loginCheck(accessToken);
        return DataResponseDto.of(response);
    }
}
