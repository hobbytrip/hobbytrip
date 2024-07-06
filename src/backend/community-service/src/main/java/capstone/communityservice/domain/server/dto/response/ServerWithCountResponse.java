package capstone.communityservice.domain.server.dto.response;

import capstone.communityservice.domain.server.entity.Server;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServerWithCountResponse {
    private Long serverId;

    private String serverName;

    private int userCount;


    public static ServerWithCountResponse of(Server server) {
        return ServerWithCountResponse.builder()
                .serverId(server.getId())
                .serverName(server.getName())
                .userCount(server.getServerUsers().size())
                .build();
    }
}
