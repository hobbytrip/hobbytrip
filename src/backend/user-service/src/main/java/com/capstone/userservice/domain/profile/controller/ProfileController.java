package com.capstone.userservice.domain.profile.controller;


import com.capstone.userservice.domain.profile.dto.request.ProfileStatusMessageRequestDto;
import com.capstone.userservice.domain.profile.dto.response.ProfileResponseDto;
import com.capstone.userservice.domain.profile.dto.response.ProfileStatusMessageResponseDto;
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

    @GetMapping("")
    public ResponseEntity<ProfileResponseDto> read(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long userId = tokenUtil.getUserId(token);

        return ResponseEntity.ok(profileService.read(userId));
    }


    @PatchMapping("/statusMessage")
    public ResponseEntity<ProfileStatusMessageResponseDto> messageUpdate(
            @RequestBody ProfileStatusMessageRequestDto requestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long userId = tokenUtil.getUserId(token);

        return ResponseEntity.ok(profileService.statusMessageModify(requestDto, userId));
    }
}
