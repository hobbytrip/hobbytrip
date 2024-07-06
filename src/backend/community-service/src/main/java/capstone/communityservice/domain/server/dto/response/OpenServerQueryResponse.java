package capstone.communityservice.domain.server.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenServerQueryResponse {
    private Long serverId;
    private Long managerId;
    private String profile;
    private String description;
    private boolean open;
    private Long userCount;

    public static OpenServerQueryResponse of(OpenServerQuery openServerQuery) {
        return OpenServerQueryResponse.builder()
                .serverId(openServerQuery.getId())
                .managerId(openServerQuery.getManagerId())
                .profile(openServerQuery.getProfile())
                .description(openServerQuery.getDescription())
                .open(openServerQuery.isOpen())
                .userCount(openServerQuery.getUserCount())
                .build();
    }
}
