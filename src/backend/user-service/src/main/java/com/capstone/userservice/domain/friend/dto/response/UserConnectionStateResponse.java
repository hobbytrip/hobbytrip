package com.capstone.userservice.domain.friend.dto.response;

import com.capstone.userservice.domain.friend.dto.ConnectionState;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConnectionStateResponse {

    private Map<Long, ConnectionState> usersConnectionState;
}