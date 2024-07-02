package capstone.communityservice.domain.channel.dto.request;

import capstone.communityservice.domain.channel.entity.ChannelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChannelCreateRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    private Long categoryId;

    @NotNull
    private ChannelType channelType;

    @NotBlank
    private String name;
}
