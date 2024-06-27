package capstone.communityservice.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryCreateRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotBlank
    private String name;
}
