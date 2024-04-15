package com.capstone.userservice.domain.profile.dto.response;


import com.capstone.userservice.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileStatusMessageResponseDto {
    private String statusMessage;
    private LocalDateTime modifiedAt;


    public static ProfileStatusMessageResponseDto from(User user) {
        return ProfileStatusMessageResponseDto.builder()
                .statusMessage(user.getStatusMessage())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
