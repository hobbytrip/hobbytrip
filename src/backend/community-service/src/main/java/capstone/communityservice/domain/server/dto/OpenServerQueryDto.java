package capstone.communityservice.domain.server.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenServerQueryDto {
    private Long serverId;
    private Long managerId;
    private String profile;
    private String description;
    private boolean open;
    private Long userCount;

    public static OpenServerQueryDto of(OpenServerQuery openServerQuery) {
        return OpenServerQueryDto.builder()
                .serverId(openServerQuery.getId())
                .managerId(openServerQuery.getManagerId())
                .profile(openServerQuery.getProfile())
                .description(openServerQuery.getDescription())
                .open(openServerQuery.isOpen())
                .userCount(openServerQuery.getUserCount())
                .build();
    }
}
