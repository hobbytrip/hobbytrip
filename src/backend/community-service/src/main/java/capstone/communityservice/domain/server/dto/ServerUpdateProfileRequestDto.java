package capstone.communityservice.domain.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerUpdateProfileRequestDto {
    @NotNull
    private Long managerId;

    @NotNull
    private Long serverId;
}
