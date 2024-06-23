package capstone.communityservice.domain.channel.dto;

import capstone.communityservice.domain.category.dto.CategoryResponseDto;
import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChannelResponseDto {
    @NotNull
    private Long channelId;

    private Long categoryId;

    @NotNull
    private ChannelType channelType;

    @NotBlank
    private String name;

    public static ChannelResponseDto of(Channel channel){
        return ChannelResponseDto.builder()
                .channelId(channel.getId())
                .categoryId(channel.getCategoryId())
                .channelType(channel.getChannelType())
                .name(channel.getName())
                .build();
    }
}
