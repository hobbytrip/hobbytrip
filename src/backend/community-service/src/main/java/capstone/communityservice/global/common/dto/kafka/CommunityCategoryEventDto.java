package capstone.communityservice.global.common.dto.kafka;

import capstone.communityservice.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityCategoryEventDto {

    private String type;

    private Long categoryId;

    private String name;

    public static CommunityCategoryEventDto of(String type, Category category){
        return CommunityCategoryEventDto.builder()
                .type(type)
                .categoryId(category.getId())
                .name(category.getName())
                .build();
    }
}
