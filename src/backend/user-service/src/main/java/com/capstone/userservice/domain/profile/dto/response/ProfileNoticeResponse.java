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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileNoticeResponse {

    private Long userId;
    private Boolean notice;
    private LocalDateTime modifiedAt;

    public static ProfileNoticeResponse from(User user) {
        return ProfileNoticeResponse.builder()
                .userId(user.getUserId())
                .notice(user.getNotificationEnabled())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
