package capstone.communityservice.global.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConnectionStateResponse {

    Map<Long, ConnectionState> usersConnectionState;
}
