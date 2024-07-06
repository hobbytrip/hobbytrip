package capstone.communityservice.domain.user.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserServerDmInfo {
    private List<Long> dmIds;
    private List<Long> serverIds;

    public static UserServerDmInfo of(List<Long> serverIds, List<Long> dmIds) {
        return UserServerDmInfo.builder()
                .dmIds(dmIds)
                .serverIds(serverIds)
                .build();
    }
}
