package capstone.communityservice.domain.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerUserDeleteRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;

    @NotNull
    private Long serverUserId;

}
