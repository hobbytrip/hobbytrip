package com.capstone.userservice.domain.user.dto.response;

import com.capstone.userservice.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserFeignResponse {

    private Long originalId;

    private String email;

    private String name;

    private String profile; //profile image url

    public static UserFeignResponse from(User user) {
        return UserFeignResponse.builder()
                .originalId(user.getUserId())
                .email(user.getEmail())
                .name(user.getUsername())
                .profile(user.getProfileImage())
                .build();
    }
}
