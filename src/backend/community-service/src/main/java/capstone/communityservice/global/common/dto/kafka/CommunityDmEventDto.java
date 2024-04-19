package capstone.communityservice.global.common.dto.kafka;

import capstone.communityservice.domain.dm.entity.Dm;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityDmEventDto {

    private String type;

    private Long dmId;

    private String name;

    private String profile;

    public static CommunityDmEventDto of(String type, Dm dm){
        return CommunityDmEventDto.builder()
                .type(type)
                .dmId(dm.getId())
                .profile(dm.getProfile())
                .name(dm.getName())
                .build();
    }
}
