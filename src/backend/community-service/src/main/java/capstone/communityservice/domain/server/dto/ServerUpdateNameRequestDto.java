package capstone.communityservice.domain.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerUpdateNameRequestDto {

    @NotNull
    private Long managerId;

    @NotNull
    private Long serverId;

    @NotBlank
    private String name;
}
