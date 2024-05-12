package com.capstone.userservice.global.common.dto.kafka;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendReadResponse {
    private Long userId;
    private Long friendId;
    private Boolean isOffline;
    private String friendImageUrl;
    private String friendName;

}
