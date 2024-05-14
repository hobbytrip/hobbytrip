package capstone.chatservice.infra.kafka.consumer.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityChannelEventDto {

    private String type;

    private Long serverId;

    private Long userId;

    private Long channelId;

    private Long categoryId;

    private ChannelType channelType;

    private String name;
}
