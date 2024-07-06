package capstone.communityservice.domain.category.dto.response;

import capstone.communityservice.domain.category.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryResponse {

    @NotNull
    private Long categoryId;

    @NotNull
    private String name;

    public static CategoryResponse of(Category category){
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .build();
    }
}
