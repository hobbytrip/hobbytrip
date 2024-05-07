package com.capstone.userservice.domain.user.controller;


import com.capstone.userservice.domain.user.dto.TokenRequest;
import com.capstone.userservice.domain.user.dto.UserRequest;
import com.capstone.userservice.domain.user.service.AuthService;
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
public class AuthController {
    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequest tokenRequest,
                                            UserRequest userRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.reissue(tokenRequest, userRequest, response));
    }

    //true 시 로그인 되어있는 상태
    @PostMapping("/loginCheck")
    public ResponseEntity<Boolean> loginCheck(@RequestBody String accessToken) {
        return ResponseEntity.ok(authService.loginCheck(accessToken));
    }
}