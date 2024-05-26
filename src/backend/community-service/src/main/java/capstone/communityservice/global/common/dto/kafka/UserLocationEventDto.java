package capstone.communityservice.global.common.dto.kafka;

import capstone.communityservice.domain.channel.entity.ChannelType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLocationEventDto {

    private Long userId;
    private Long serverId;
    private Long channelId;

    public static UserLocationEventDto of(
            Long userId,
            Long serverId,
            Long channelId
    ){
        return UserLocationEventDto.builder()
                .userId(userId)
                .serverId(serverId)
                .channelId(channelId)
                .build();
    }
}
