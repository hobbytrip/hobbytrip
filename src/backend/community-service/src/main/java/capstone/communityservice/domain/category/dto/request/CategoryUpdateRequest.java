package capstone.communityservice.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryUpdateRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotNull
    private Long categoryId;

    @NotBlank
    private String name;
}
