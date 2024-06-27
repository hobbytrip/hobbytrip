package capstone.communityservice.domain.channel.dto.response;

import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChannelResponse {
    @NotNull
    private Long channelId;

    private Long categoryId;

    @NotNull
    private ChannelType channelType;

    @NotBlank
    private String name;

    public static ChannelResponse of(Channel channel){
        return ChannelResponse.builder()
                .channelId(channel.getId())
                .categoryId(channel.getCategoryId())
                .channelType(channel.getChannelType())
                .name(channel.getName())
                .build();
    }
}
