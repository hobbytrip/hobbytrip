package com.capstone.userservice.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequestDto {

    private String accessToken;
    private String refreshToken;
}

