package capstone.communityservice.domain.server.dto.response;

import capstone.communityservice.domain.server.entity.ServerUser;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class ServerUserInfo {
    private Long userId;
    private String name;

    public static ServerUserInfo of(ServerUser findServerUser) {
        return ServerUserInfo.builder()
                .userId(findServerUser.getUser().getId())
                .name(findServerUser.getName())
                .build();
    }
}
