package capstone.communityservice.domain.channel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChannelDeleteRequestDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotNull
    private Long channelId;
}
