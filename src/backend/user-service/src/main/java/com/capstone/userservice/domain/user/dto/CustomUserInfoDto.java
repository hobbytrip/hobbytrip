package com.capstone.userservice.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserInfoDto extends UserDto {
    private Long userId;
    private String email;
    private String name;
    private String password;
}
