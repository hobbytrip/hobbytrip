package com.capstone.userservice.domain.profile.dto.response;

import com.capstone.userservice.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileImageResponse {

    private String imageUrl;
    private LocalDateTime modifiedAt;

    public static ProfileImageResponse from(User user) {
        return ProfileImageResponse.builder()
                .imageUrl(user.getProfileImage())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
