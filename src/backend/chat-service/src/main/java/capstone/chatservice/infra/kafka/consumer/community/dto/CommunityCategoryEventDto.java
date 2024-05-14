package capstone.chatservice.infra.kafka.consumer.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityCategoryEventDto {

    private String type;

    private Long serverId;

    private Long categoryId;

    private String name;
}
