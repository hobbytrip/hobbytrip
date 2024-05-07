package com.capstone.userservice.domain.friend.dto.response;

import com.capstone.userservice.domain.friend.entity.FriendshipStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendshipResponse {
    private Long id;
    private String userEmail;
    private String friendEmail;
    private FriendshipStatus status;
    private Boolean isFrom;
    private Long user;

}
