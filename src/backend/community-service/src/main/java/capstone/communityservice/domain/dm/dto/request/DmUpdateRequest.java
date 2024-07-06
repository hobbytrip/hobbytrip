package capstone.communityservice.domain.dm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DmUpdateRequest {

    @NotNull
    private Long dmId;

    @NotBlank
    private String name;
}
