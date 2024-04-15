package com.capstone.userservice.domain.profile.service;


import com.capstone.userservice.domain.profile.dto.request.ProfileStatusMessageRequestDto;
import com.capstone.userservice.domain.profile.dto.response.ProfileResponseDto;
import com.capstone.userservice.domain.profile.dto.response.ProfileStatusMessageResponseDto;
import com.capstone.userservice.domain.profile.exception.ProfileException;
import com.capstone.userservice.domain.profile.repository.ProfileRepository;
import com.capstone.userservice.domain.user.entity.User;
import com.capstone.userservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public ProfileResponseDto read(Long userId) {
        User userInfo = validateInProfile(userId);

        ProfileResponseDto profileDto = ProfileResponseDto.from(userInfo);
        return profileDto;
    }

    @Transactional
    public ProfileStatusMessageResponseDto statusMessageModify(@RequestBody ProfileStatusMessageRequestDto requestDto,
                                                               Long userId) {
        User userInfo = validateInProfile(userId);
        userInfo.setStatusMessage(requestDto.getStatusMessage(), requestDto.getModifiedAt());
        return ProfileStatusMessageResponseDto.from(profileRepository.save(userInfo));
    }


    public User validateInProfile(Long userId) {
        return profileRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(Code.NOT_FOUND,
                        "Not Found Profile"));
    }
}
