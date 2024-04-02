package com.capstone.userservice.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private Date birthdate;
    private boolean notificationEnabled;
    private Date createAt;
}
