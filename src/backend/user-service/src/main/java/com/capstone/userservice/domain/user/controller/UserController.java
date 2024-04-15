package com.capstone.userservice.domain.user.controller;


import com.capstone.userservice.domain.user.dto.TokenRequest;
import com.capstone.userservice.domain.user.dto.UserDeleteRequest;
import com.capstone.userservice.domain.user.dto.UserRequest;
import com.capstone.userservice.domain.user.dto.UserResponse;
import com.capstone.userservice.domain.user.service.UserService;
import com.capstone.userservice.global.common.dto.TokenDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.signup(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequest userRequest, HttpServletResponse response) {
        return ResponseEntity.ok(userService.login(userRequest, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(userService.logout(tokenRequest));
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody UserDeleteRequest request) {
        return ResponseEntity.ok(userService.deleteUser(request));
    }
}
