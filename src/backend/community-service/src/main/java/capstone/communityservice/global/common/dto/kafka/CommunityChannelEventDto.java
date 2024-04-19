package capstone.communityservice.global.common.dto.kafka;

import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityChannelEventDto {

    private String type;

    private Long channelId;

    private Long categoryId;

    private ChannelType channelType;

    private String name;

    public static CommunityChannelEventDto of(String type, Channel channel){
        return CommunityChannelEventDto.builder()
                .type(type)
                .channelId(channel.getId())
                .categoryId(channel.getCategoryId())
                .channelType(channel.getChannelType())
                .name(channel.getName())
                .build();
    }
}
