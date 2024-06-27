package capstone.communityservice.domain.category.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryDeleteRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotNull
    private Long categoryId;
}
