package capstone.chatservice.infra.kafka.consumer.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityDmEventDto {

    private String type;

    private Long dmId;

    private String name;

    private String profile;
}
