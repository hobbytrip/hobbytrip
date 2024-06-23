package capstone.communityservice.domain.dm.dto;

import capstone.communityservice.domain.dm.entity.Dm;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DmReadQueryDto {

    private Long dmId;

    private String name;

    private String profile;

    public static DmReadQueryDto of(Dm dm){
        return DmReadQueryDto.builder()
                .dmId(dm.getId())
                .name(dm.getName())
                .profile(dm.getProfile())
                .build();
    }
}
