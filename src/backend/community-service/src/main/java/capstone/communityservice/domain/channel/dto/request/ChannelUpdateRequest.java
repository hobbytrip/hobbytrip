package capstone.communityservice.domain.channel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChannelUpdateRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotNull
    private Long channelId;
    
    private Long categoryId;

    @NotBlank
    private String name;
}
