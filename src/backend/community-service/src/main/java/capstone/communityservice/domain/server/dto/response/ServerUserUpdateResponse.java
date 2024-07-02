package capstone.communityservice.domain.server.dto.response;

import capstone.communityservice.domain.server.entity.ServerUser;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class ServerUserUpdateResponse {
    private Long serverId;

    private Long userId;

    private String name;

    public static ServerUserUpdateResponse of(ServerUser findServerUser, Long serverId, Long userId) {
        return ServerUserUpdateResponse.builder()
                .serverId(serverId)
                .userId(userId)
                .name(findServerUser.getName())
                .build();
    }
}
