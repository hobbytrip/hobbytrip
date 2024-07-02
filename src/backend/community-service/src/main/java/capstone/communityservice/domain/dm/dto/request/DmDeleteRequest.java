package capstone.communityservice.domain.dm.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DmDeleteRequest {

    @NotBlank
    private Long dmId;
}
