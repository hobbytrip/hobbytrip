package capstone.communityservice.domain.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerCreateRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String name;
}
