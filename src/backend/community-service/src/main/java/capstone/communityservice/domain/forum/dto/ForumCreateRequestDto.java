package capstone.communityservice.domain.forum.dto;

import capstone.communityservice.domain.channel.entity.Channel;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ForumCreateRequestDto {
    @NotNull
    private Long serverId;

    @NotNull
    private Long channelId;

    @NotBlank
    private String title;

    @NotNull
    private Long userId;

    @NotBlank
    private String content;
}
