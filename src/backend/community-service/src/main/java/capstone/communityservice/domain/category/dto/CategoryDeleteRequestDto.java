package capstone.communityservice.domain.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryDeleteRequestDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotNull
    private Long categoryId;
}
