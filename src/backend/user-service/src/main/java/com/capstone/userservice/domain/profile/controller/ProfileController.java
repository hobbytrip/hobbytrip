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
import com.capstone.userservice.domain.user.dto.response.UserFeignResponse;
import com.capstone.userservice.global.common.dto.DataResponseDto;
import com.capstone.userservice.global.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final TokenUtil tokenUtil;
    private final String Header = "Authorization";

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> read(HttpServletRequest request) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        Long userId = tokenUtil.getUserId(temp);

        return ResponseEntity.ok(profileService.read(userId));
    }

    @PatchMapping("/profile/statusMessage")
    public DataResponseDto<Object> messageUpdate(@RequestBody ProfileStatusMessageRequest requestDto,
                                                 HttpServletRequest request) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        Long userId = tokenUtil.getUserId(temp);

        ProfileStatusMessageResponse response = profileService.statusMessageModify(requestDto, userId);

        return DataResponseDto.of(response);
    }

    @PatchMapping("/profile/nickname")
    public DataResponseDto<Object> nickNameUpdate(@Valid @RequestBody ProfileNicknameRequest requestDto,
                                                  HttpServletRequest request) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        Long userId = tokenUtil.getUserId(temp);

        ProfileNicknameResponse response = profileService.nickNameModify(requestDto, userId);

        return DataResponseDto.of(response);
    }

    @PatchMapping("/profile/notice")
    public DataResponseDto<Object> noticeUpdate(
            @RequestBody ProfileNoticeRequest requestDto, HttpServletRequest request) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        Long userId = tokenUtil.getUserId(temp);

        ProfileNoticeResponse response = profileService.noticeModify(requestDto, userId);
        return DataResponseDto.of(response);
    }

    @PostMapping("/profile/image")
    public DataResponseDto<Object> imageUpdate(HttpServletRequest request,
                                               @RequestPart(value = "image", required = false) MultipartFile image) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        Long userId = tokenUtil.getUserId(temp);

        ProfileImageResponse response = profileService.imageModify(image, userId);
        return DataResponseDto.of(response);
    }

    @GetMapping("/profile/image")
    public DataResponseDto<Object> imageDelete(HttpServletRequest request,
                                               @RequestParam("delete") String image) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        Long userId = tokenUtil.getUserId(temp);

        Boolean response = profileService.imageDelete(image, userId);
        return DataResponseDto.of(response);
    }

    @GetMapping("/feign/profile/info/{userId}")
    public UserFeignResponse getUser(@PathVariable Long userId) {
        return profileService.userProfileRead(userId);
    }

    public String trimToken(String token) {
        return token.split(" ")[1].trim();
    }
}
