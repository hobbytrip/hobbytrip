package capstone.communityservice.domain.server.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerUserDeleteRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotNull
    private Long serverUserId;
}
