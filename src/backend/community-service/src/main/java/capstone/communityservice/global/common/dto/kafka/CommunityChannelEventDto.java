package capstone.communityservice.global.common.dto.kafka;

import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
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

    public static CommunityChannelEventDto of(String type, Channel channel, Long serverId){
        return CommunityChannelEventDto.builder()
                .type(type)
                .serverId(serverId)
                .channelId(channel.getId())
                .categoryId(channel.getCategoryId())
                .channelType(channel.getChannelType())
                .name(channel.getName())
                .build();
    }

    public static CommunityChannelEventDto of(String type, Long userId, Long channelId, ChannelType channelType){
        return CommunityChannelEventDto.builder()
                .type(type)
                .userId(userId)
                .channelId(channelId)
                .channelType(channelType)
                .build();
    }
}
