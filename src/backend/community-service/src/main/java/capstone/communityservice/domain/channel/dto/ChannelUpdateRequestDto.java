package capstone.communityservice.domain.channel.dto;

import capstone.communityservice.domain.channel.entity.ChannelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChannelUpdateRequestDto {
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
