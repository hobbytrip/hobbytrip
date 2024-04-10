package com.capstone.userservice.domain.user.controller;


import com.capstone.userservice.domain.user.dto.TokenRequestDto;
import com.capstone.userservice.domain.user.dto.UserRequestDto;
import com.capstone.userservice.domain.user.dto.UserResponseDto;
import com.capstone.userservice.domain.user.service.AuthService;
import com.capstone.userservice.global.common.dto.TokenDto;
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
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.login(userRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto,
                                            UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto, userRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.logout(tokenRequestDto));
    }

    //true 시 로그인 되어있는 상태
    @PostMapping("/loginCheck")
    public ResponseEntity<Boolean> loginCheck(@RequestBody String accessToken) {
        return ResponseEntity.ok(authService.loginCheck(accessToken));
    }
}
