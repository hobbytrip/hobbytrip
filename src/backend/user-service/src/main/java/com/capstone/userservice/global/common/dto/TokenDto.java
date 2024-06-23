package com.capstone.userservice.global.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {
    private String grantType; //Bearer
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresIn;
}
