package com.capstone.userservice.domain.profile.dto.request;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileStatusMessageRequestDto {
    private String statusMessage;
    private LocalDateTime modifiedAt;
}
