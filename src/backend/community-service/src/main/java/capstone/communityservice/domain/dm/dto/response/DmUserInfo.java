package capstone.communityservice.domain.dm.dto.response;

import capstone.communityservice.domain.dm.entity.DmUser;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class DmUserInfo {
    private Long userId;
    private String name;

    public static DmUserInfo of(DmUser dmUser) {
        return DmUserInfo.builder()
                .userId(dmUser.getUser().getId())
                .name(dmUser.getUser().getName())
                .build();
    }
}
