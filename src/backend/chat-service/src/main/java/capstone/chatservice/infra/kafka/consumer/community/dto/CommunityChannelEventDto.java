package capstone.chatservice.infra.kafka.consumer.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityChannelEventDto {

    private String type;

    private Long serverId;

    private Long userId;

    private Long channelId;

    private Long categoryId;

    private ChannelType channelType;

    private String name;
}
