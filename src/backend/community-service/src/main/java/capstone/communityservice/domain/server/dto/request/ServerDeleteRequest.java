package capstone.communityservice.domain.server.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerDeleteRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

}
