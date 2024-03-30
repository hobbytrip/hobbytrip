package capstone.communityservice.domain.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServerCreateRequestDto {

    @NotNull
    private Long managerId;

    @NotBlank
    private String name;
}
