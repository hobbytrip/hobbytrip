package com.capstone.userservice.global.common.dto.kafka;


import com.capstone.userservice.domain.friend.entity.Friendship;
import com.capstone.userservice.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStatusEventDto {
    private Long userId;
    private List<Long> friendshipId;

    public static UserStatusEventDto from(User user) {
        return UserStatusEventDto.builder()
                .friendshipId(user.getFriendshipList().stream()
                        .map(Friendship::getId)
                        .collect(Collectors.toList()))
                .userId(user.getUserId())
                .build();
    }
}
