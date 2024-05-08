package com.capstone.userservice.domain.friend.dto.response;


import com.capstone.userservice.domain.friend.dto.FriendStatusDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFriendsStatusResponse {
    private List<FriendStatusDto> friendsStatus;
}
