package capstone.communityservice.domain.forum.dto.request;

import capstone.communityservice.domain.forum.entity.ForumCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ForumUpdateRequest {
    @NotNull
    private Long serverId;

    @NotNull
    private Long channelId;

    @NotNull
    private Long forumId;

    @NotBlank
    private String title;

    @NotNull
    private Long userId;

    @NotBlank
    private String content;

    private ForumCategory category;
}
