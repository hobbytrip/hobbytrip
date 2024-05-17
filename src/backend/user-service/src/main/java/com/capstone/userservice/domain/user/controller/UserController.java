package com.capstone.userservice.domain.user.controller;


import com.capstone.userservice.domain.user.dto.request.TokenRequest;
import com.capstone.userservice.domain.user.dto.request.UserDeleteRequest;
import com.capstone.userservice.domain.user.dto.request.UserRequest;
import com.capstone.userservice.domain.user.dto.response.UserResponse;
import com.capstone.userservice.domain.user.service.UserService;
import com.capstone.userservice.global.common.dto.DataResponseDto;
import com.capstone.userservice.global.common.dto.TokenDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public DataResponseDto<Object> signup(@RequestBody UserRequest userRequest) {
        UserResponse response = userService.signup(userRequest);
        return DataResponseDto.of(response);
    }

    @PostMapping("/login")
    public DataResponseDto<TokenDto> login(@RequestBody UserRequest userRequest, HttpServletResponse response) {
        TokenDto tokenDto = userService.login(userRequest, response);
        return DataResponseDto.of(tokenDto);
    }

    @PostMapping("/user-logout")
    public DataResponseDto<Object> logout(@RequestBody TokenRequest tokenRequest) {
        Boolean response = userService.logout(tokenRequest);
        return DataResponseDto.of(response);
    }

    @PostMapping("/delete")
    public DataResponseDto<Object> delete(@Valid @RequestBody UserDeleteRequest request) {
        Boolean response = userService.deleteUser(request);
        return DataResponseDto.of(response);
    }
}
