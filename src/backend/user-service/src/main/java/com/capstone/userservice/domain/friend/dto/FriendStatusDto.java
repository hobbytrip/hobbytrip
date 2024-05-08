package com.capstone.userservice.domain.friend.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendStatusDto {
    private Long friendId;
    private Boolean isOffline;
}
