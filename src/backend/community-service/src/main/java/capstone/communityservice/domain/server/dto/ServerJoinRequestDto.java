package capstone.communityservice.domain.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerJoinRequestDto {

    @NotNull
    private Long serverId;

    @NotNull
    private Long userId;

    private String invitationCode;
}
