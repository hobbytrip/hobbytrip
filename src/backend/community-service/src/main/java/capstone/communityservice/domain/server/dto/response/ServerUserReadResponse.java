package capstone.communityservice.domain.server.dto.response;

import capstone.communityservice.domain.server.entity.Server;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerUserReadResponse {

    private Long serverId;

    private String name;

    private String profile;

    public static ServerUserReadResponse of(Server server){
        return ServerUserReadResponse.builder()
                .serverId(server.getId())
                .name(server.getName())
                .profile(server.getProfile())
                .build();
    }
}
