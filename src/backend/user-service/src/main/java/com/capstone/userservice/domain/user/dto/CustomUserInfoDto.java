package com.capstone.userservice.domain.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserInfoDto extends UserDto {
    @NotNull
    private Long userId;
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
}
