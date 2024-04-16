package com.capstone.userservice.domain.profile.dto.response;


import com.capstone.userservice.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResponse {

    private Long userId;
    private String name;
    private String nickname;
    private String email;
    private String profileImage;
    private String statusMessage;
    private LocalDateTime modifiedAt;

    public static ProfileResponse from(User user) {
        return ProfileResponse.builder()
                .userId(user.getUserId())
                .name(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .statusMessage(user.getStatusMessage())
                .modifiedAt(user.getModifiedAt())
                .build();

    }
}
