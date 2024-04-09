package com.capstone.userservice.domain.user.controller;


import com.capstone.userservice.domain.user.dto.TokenRequestDto;
import com.capstone.userservice.domain.user.dto.UserRequestDto;
import com.capstone.userservice.domain.user.dto.UserResponseDto;
import com.capstone.userservice.domain.user.service.AuthService;
import com.capstone.userservice.global.common.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.signup(userRequestDto));
    }


    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.login(userRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<JwtTokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto,
                                               UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto, userRequestDto));
    }

}
