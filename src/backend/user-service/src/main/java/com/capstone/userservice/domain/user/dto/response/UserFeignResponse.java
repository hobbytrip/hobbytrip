package com.capstone.userservice.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserFeignResponse {

    private Long originalId;

    private String email;

    private String name;

    private String profile;
}
