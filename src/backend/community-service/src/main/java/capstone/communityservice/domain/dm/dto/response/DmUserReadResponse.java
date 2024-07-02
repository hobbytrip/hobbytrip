package capstone.communityservice.domain.dm.dto.response;

import capstone.communityservice.domain.dm.entity.Dm;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DmUserReadResponse {

    private Long dmId;

    private String name;

    private String profile;

    public static DmUserReadResponse of(Dm dm){
        return DmUserReadResponse.builder()
                .dmId(dm.getId())
                .name(dm.getName())
                .profile(dm.getProfile())
                .build();
    }
}
