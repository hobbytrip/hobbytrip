package com.capstone.userservice.domain.profile.dto;


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
public class ProfileResponseDto {

    private Long userId;
    private String name;
    private String nickname;
    private String email;
    private String profileImage;
    private String phone;
    private String statusMessage;
    private LocalDateTime modifiedAt;

    public static ProfileResponseDto of(User user) {
        return new ProfileResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getProfileImage(),
                user.getPhone(),
                user.getStatusMessage(),
                user.getModifiedAt()
        );
    }
}
