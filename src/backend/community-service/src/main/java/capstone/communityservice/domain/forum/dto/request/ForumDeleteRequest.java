package capstone.communityservice.domain.forum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ForumDeleteRequest {
    @NotNull
    private Long serverId;

    @NotNull
    private Long channelId;

    @NotNull
    private Long forumId;

    @NotNull
    private Long userId;
}
