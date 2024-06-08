package capstone.communityservice.domain.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerProfileDeleteRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long serverId;
}
