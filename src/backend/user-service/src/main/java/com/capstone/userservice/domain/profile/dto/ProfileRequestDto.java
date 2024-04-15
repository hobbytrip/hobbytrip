package com.capstone.userservice.domain.profile.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class ProfileRequestDto {
    @NotNull
    private Long userId;

}
