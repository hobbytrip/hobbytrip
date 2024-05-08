package capstone.communityservice.domain.forum.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ForumDeleteRequestDto {
    @NotNull
    private Long channelId;

    @NotNull
    private Long forumId;

    @NotNull
    private Long userId;
}
