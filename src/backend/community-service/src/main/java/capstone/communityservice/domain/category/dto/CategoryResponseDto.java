package capstone.communityservice.domain.category.dto;

import capstone.communityservice.domain.category.entity.Category;
import capstone.communityservice.domain.server.entity.Server;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    public static CategoryResponseDto of(Category category){
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
