package capstone.communityservice.domain.server.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerJoinRequest {

    @NotNull
    private Long serverId;

    @NotNull
    private Long userId;

    private String invitationCode;
}
