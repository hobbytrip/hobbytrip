package capstone.communityservice.domain.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerUserUpdateDto {
    @NotNull
    private Long serverId;

    @NotNull
    private Long userId;

    @NotBlank
    private String name;

}
