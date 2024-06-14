package com.capstone.userservice.domain.friend.dto.response;

import com.capstone.userservice.domain.friend.entity.FriendshipStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WaitingFriendListResponse {

    private Long friendshipId;
    private String friendEmail;
    private String friendName;
    private FriendshipStatus status;
    private String imageUrl;
}
