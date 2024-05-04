package com.capstone.userservice.domain.profile.controller;


import com.capstone.userservice.domain.profile.dto.request.ProfileNicknameRequest;
import com.capstone.userservice.domain.profile.dto.request.ProfileNoticeRequest;
import com.capstone.userservice.domain.profile.dto.request.ProfileStatusMessageRequest;
import com.capstone.userservice.domain.profile.dto.response.ProfileImageResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileNicknameResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileNoticeResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileStatusMessageResponse;
import com.capstone.userservice.domain.profile.service.ProfileService;
import com.capstone.userservice.global.common.dto.DataResponseDto;
import com.capstone.userservice.global.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public DataResponseDto<Object> messageUpdate(@RequestBody ProfileStatusMessageRequest requestDto,
                                                 HttpServletRequest request) {
        String token = request.getHeader(Header);
        Long userId = tokenUtil.getUserId(token);

        ProfileStatusMessageResponse response = profileService.statusMessageModify(requestDto, userId);

        return DataResponseDto.of(response);
    }

    @PatchMapping("/nickname")
    public DataResponseDto<Object> nickNameUpdate(@Valid @RequestBody ProfileNicknameRequest requestDto,
                                                  HttpServletRequest request) {
        String token = request.getHeader(Header);
        Long userId = tokenUtil.getUserId(token);

        ProfileNicknameResponse response = profileService.nickNameModify(requestDto, userId);

        return DataResponseDto.of(response);
    }

    @PatchMapping("/notice")
    public DataResponseDto<Object> noticeUpdate(
            @RequestBody ProfileNoticeRequest requestDto, HttpServletRequest request) {
        String token = request.getHeader(Header);
        Long userId = tokenUtil.getUserId(token);

        ProfileNoticeResponse response = profileService.noticeModify(requestDto, userId);
        return DataResponseDto.of(response);
    }

    @PostMapping("/image")
    public DataResponseDto<Object> imageUpdate(HttpServletRequest request,
                                               @RequestPart(value = "image", required = false) MultipartFile image) {
        String token = request.getHeader(Header);
        Long userId = tokenUtil.getUserId(token);

        ProfileImageResponse response = profileService.imageModify(image, userId);
        return DataResponseDto.of(response);
    }

    @GetMapping("/image/delete")
    public DataResponseDto<Object> imageDelete(HttpServletRequest request,
                                               @RequestParam String image) {
        String token = request.getHeader(Header);
        Long userId = tokenUtil.getUserId(token);

        Boolean response = profileService.imageDelete(image, userId);
        return DataResponseDto.of(response);
    }
}
