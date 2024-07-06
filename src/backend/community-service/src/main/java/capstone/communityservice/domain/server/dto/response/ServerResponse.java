package capstone.communityservice.domain.server.dto.response;

import capstone.communityservice.domain.server.entity.Server;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ServerResponse {

    private Long serverId;

    private Long managerId;

    private String profile;

    private String description;

    private String name;

    private boolean open;

    public static ServerResponse of(Server server){
        return ServerResponse.builder()
                .serverId(server.getId())
                .managerId(server.getManagerId())
                .profile(server.getProfile())
                .name(server.getName())
                .open(server.isOpen())
                .description(server.getDescription())
                .build();
    }
}
