package com.capstone.userservice.domain.profile.dto.response;


import com.capstone.userservice.domain.user.entity.User;
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
    private boolean notice;

    public static ProfileNoticeResponse from(User user) {
        return ProfileNoticeResponse.builder()
                .userId(user.getUserId())
                .notice(user.isNotificationEnabled())
                .build();
    }
}
