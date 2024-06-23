package capstone.communityservice.global.common.dto.kafka;

import capstone.communityservice.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityCategoryEventDto {

    private String type;

    private Long serverId;

    private Long categoryId;

    private String name;

    public static CommunityCategoryEventDto of(String type, Category category, Long serverId){
        return CommunityCategoryEventDto.builder()
                .type(type)
                .serverId(serverId)
                .categoryId(category.getId())
                .name(category.getName())
                .build();
    }
}
