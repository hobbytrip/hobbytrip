package capstone.communityservice.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryCreateRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotBlank
    private String name;
}
