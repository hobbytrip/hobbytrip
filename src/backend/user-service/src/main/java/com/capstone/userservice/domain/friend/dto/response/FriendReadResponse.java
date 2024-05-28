package com.capstone.userservice.domain.friend.dto.response;


import com.capstone.userservice.domain.friend.dto.ConnectionState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendReadResponse {
    private Long userId;
    private Long friendId;
    private ConnectionState connectionState;
    private String friendImageUrl;
    private String friendName;

}
