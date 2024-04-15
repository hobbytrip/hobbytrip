package com.capstone.userservice.domain.user.dto;


import com.capstone.userservice.domain.user.entity.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private String username;
    private String birthdate;
    private boolean notificationEnabled;
    private LocalDateTime createdAt;

    public static UserResponseDto of(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new UserResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getUsername(),
                user.getBirthdate() != null ? formatter.format(
                        user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) : null,
                // Date를 String으로 변환
                user.isNotificationEnabled(),
                user.getCreatedAt()
        );
    }
}
