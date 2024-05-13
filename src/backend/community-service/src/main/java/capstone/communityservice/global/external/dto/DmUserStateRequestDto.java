package capstone.communityservice.global.external.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DmUserStateRequestDto {
    private List<Long> userIds;

    public static DmUserStateRequestDto of(List<Long> userIds){
        return DmUserStateRequestDto.builder()
                .userIds(userIds)
                .build();
    }
}
