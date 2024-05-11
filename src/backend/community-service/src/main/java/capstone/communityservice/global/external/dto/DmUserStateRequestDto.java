package capstone.communityservice.global.external.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DmUserStateRequestDto {
    private Long dmId;
    private List<Long> userIds;

    public static DmUserStateRequestDto of(Long dmId, List<Long> userIds){
        return DmUserStateRequestDto.builder()
                .dmId(dmId)
                .userIds(userIds)
                .build();
    }
}
