package capstone.communityservice.domain.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerUpdateRequest {

    @NotNull
    private Long serverId;

    @NotNull
    private Long userId;

    @NotBlank
    private String name;

    @NotBlank
    private String profile;

    private boolean open;

    private String description;

}
