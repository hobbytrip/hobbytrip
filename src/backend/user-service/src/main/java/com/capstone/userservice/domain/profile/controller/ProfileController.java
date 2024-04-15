package com.capstone.userservice.domain.profile.controller;


import com.capstone.userservice.domain.profile.dto.request.ProfileNicknameRequest;
import com.capstone.userservice.domain.profile.dto.request.ProfileStatusMessageRequest;
import com.capstone.userservice.domain.profile.dto.response.ProfileNicknameResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileStatusMessageResponse;
import com.capstone.userservice.domain.profile.service.ProfileService;
import com.capstone.userservice.global.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final TokenUtil tokenUtil;
    private final String Header = "Authorization";

    @GetMapping("")
    public ResponseEntity<ProfileResponse> read(HttpServletRequest request) {
        String token = request.getHeader(Header);
        Long userId = tokenUtil.getUserId(token);

        return ResponseEntity.ok(profileService.read(userId));
    }

    @PatchMapping("/statusMessage")
    public ResponseEntity<ProfileStatusMessageResponse> messageUpdate(
            @RequestBody ProfileStatusMessageRequest requestDto, HttpServletRequest request) {
        String token = request.getHeader(Header);
        Long userId = tokenUtil.getUserId(token);

        return ResponseEntity.ok(profileService.statusMessageModify(requestDto, userId));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<ProfileNicknameResponse> nickNameUpdate(
            @RequestBody ProfileNicknameRequest requestDto, HttpServletRequest request) {
        String token = request.getHeader(Header);
        Long userId = tokenUtil.getUserId(token);

        return ResponseEntity.ok(profileService.nickNameModify(requestDto, userId));
    }
}
