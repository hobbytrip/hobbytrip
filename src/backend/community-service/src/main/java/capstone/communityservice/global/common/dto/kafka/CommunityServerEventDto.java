package capstone.communityservice.global.common.dto.kafka;

import capstone.communityservice.domain.server.entity.Server;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityServerEventDto{

    private String type;

    private Long serverId;

    private String profile;

    private String name;

    public static CommunityServerEventDto of(String type, Server server){
        return CommunityServerEventDto.builder()
                .type(type)
                .serverId(server.getId())
                .profile(server.getProfile())
                .name(server.getName())
                .build();
    }
}
