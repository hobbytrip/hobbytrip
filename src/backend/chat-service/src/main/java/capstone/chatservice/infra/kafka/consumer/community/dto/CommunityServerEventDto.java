package capstone.chatservice.infra.kafka.consumer.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityServerEventDto {

    private String type;

    private Long serverId;

    private String profile;

    private String name;
}
