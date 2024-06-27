package capstone.communityservice.domain.channel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChannelDeleteRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotNull
    private Long channelId;
}
