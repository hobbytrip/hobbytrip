package com.capstone.userservice.global.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtTokenDto {
    private String grantType; //Bearer
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresIn;
}
