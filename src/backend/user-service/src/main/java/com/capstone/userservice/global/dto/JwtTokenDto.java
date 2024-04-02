package com.capstone.userservice.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenDto {
    private String grantType; //Bearer
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresIn;
}
