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
public class ProfileNicknameResponse {

    private String nickName;
    private LocalDateTime modifiedAt;

    public static ProfileNicknameResponse from(User user) {
        return ProfileNicknameResponse.builder()
                .nickName(user.getNickname())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
