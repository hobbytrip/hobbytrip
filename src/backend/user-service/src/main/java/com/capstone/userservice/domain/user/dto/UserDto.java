package com.capstone.userservice.domain.user.dto;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private Long userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private Date birthdate;
    private boolean notificationEnabled;
    private Date createdAt;
}
