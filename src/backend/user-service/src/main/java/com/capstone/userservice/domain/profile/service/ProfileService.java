package com.capstone.userservice.domain.profile.service;


import com.capstone.userservice.domain.profile.dto.request.ProfileNicknameRequest;
import com.capstone.userservice.domain.profile.dto.request.ProfileStatusMessageRequest;
import com.capstone.userservice.domain.profile.dto.response.ProfileNicknameResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileStatusMessageResponse;
import com.capstone.userservice.domain.profile.exception.ProfileException;
import com.capstone.userservice.domain.profile.repository.ProfileRepository;
import com.capstone.userservice.domain.user.entity.User;
import com.capstone.userservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public ProfileResponse read(Long userId) {
        User userInfo = validateInProfile(userId);

        ProfileResponse profileDto = ProfileResponse.from(userInfo);
        return profileDto;
    }

    @Transactional
    public ProfileStatusMessageResponse statusMessageModify(ProfileStatusMessageRequest request,
                                                            Long userId) {
        User userInfo = validateInProfile(userId);
        userInfo.setStatusMessage(request.getStatusMessage());
        return ProfileStatusMessageResponse.from(profileRepository.save(userInfo));
    }

    @Transactional
    public ProfileNicknameResponse nickNameModify(ProfileNicknameRequest request, Long userId) {
        User userInfo = validateInProfile(userId);
        userInfo.setNickname(request.getNickname());
        return ProfileNicknameResponse.from(profileRepository.save(userInfo));

    }

    public User validateInProfile(Long userId) {
        return profileRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(Code.NOT_FOUND,
                        "Not Found Profile"));
    }
}
