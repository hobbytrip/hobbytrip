package capstone.communityservice.domain.server.dto;

import capstone.communityservice.domain.server.entity.Server;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerReadQueryDto {

    private Long serverId;

    private String name;

    private String profile;

    public static ServerReadQueryDto of(Server server){
        return ServerReadQueryDto.builder()
                .serverId(server.getId())
                .name(server.getName())
                .profile(server.getProfile())
                .build();
    }
}
