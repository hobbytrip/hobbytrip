package com.capstone.userservice.domain.friend.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendStatusDto {
    private Long friendId;
    private Boolean isOffline;
}
