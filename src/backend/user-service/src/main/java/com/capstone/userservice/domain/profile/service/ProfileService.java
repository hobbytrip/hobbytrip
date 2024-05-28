package com.capstone.userservice.domain.profile.service;


import com.capstone.userservice.domain.profile.dto.request.ProfileNicknameRequest;
import com.capstone.userservice.domain.profile.dto.request.ProfileNoticeRequest;
import com.capstone.userservice.domain.profile.dto.request.ProfileStatusMessageRequest;
import com.capstone.userservice.domain.profile.dto.response.ProfileImageResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileNicknameResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileNoticeResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileResponse;
import com.capstone.userservice.domain.profile.dto.response.ProfileStatusMessageResponse;
import com.capstone.userservice.domain.profile.exception.ProfileException;
import com.capstone.userservice.domain.profile.repository.ProfileRepository;
import com.capstone.userservice.domain.user.dto.response.UserFeignResponse;
import com.capstone.userservice.domain.user.entity.User;
import com.capstone.userservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final S3ImageService s3ImageService;

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

    @Transactional
    public ProfileNoticeResponse noticeModify(ProfileNoticeRequest request, Long userId) {
        User userInfo = validateInProfile(userId);
        userInfo.setNotice(request.getNotice());
        return ProfileNoticeResponse.from(profileRepository.save(userInfo));
    }

    @Transactional
    public ProfileImageResponse imageModify(MultipartFile image, Long userId) {
        User userInfo = validateInProfile(userId);
        String s3Image = s3ImageService.upload(image);
        userInfo.setProfileImage(s3Image);
        return ProfileImageResponse.from(profileRepository.save(userInfo));
    }

    @Transactional
    public Boolean imageDelete(String url, Long userId) {
        User userInfo = validateInProfile(userId);
        return s3ImageService.deleteImageFromS3(url, userInfo);
    }

    @Transactional
    public UserFeignResponse userProfileRead(Long userId) {
        User userInfo = validateInProfile(userId);
        return UserFeignResponse.from(userInfo);
    }

    public User validateInProfile(Long userId) {
        return profileRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(Code.NOT_FOUND,
                        "해당 사용자의 프로필을 찾을 수 없습니다."));
    }

}
