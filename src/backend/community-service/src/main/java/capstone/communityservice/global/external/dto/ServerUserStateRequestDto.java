package capstone.communityservice.global.external.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerUserStateRequestDto {
    private Long serverId;
    private List<Long> userIds;

    public static ServerUserStateRequestDto of(Long serverId, List<Long> userIds){
        return ServerUserStateRequestDto.builder()
                .serverId(serverId)
                .userIds(userIds)
                .build();
    }
}
