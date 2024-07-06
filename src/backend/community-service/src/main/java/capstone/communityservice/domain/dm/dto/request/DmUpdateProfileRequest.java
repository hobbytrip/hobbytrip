package capstone.communityservice.domain.dm.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DmUpdateProfileRequest {

    @NotNull
    private Long dmId;
}
