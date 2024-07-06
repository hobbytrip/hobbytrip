package capstone.communityservice.domain.dm.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DmUserDeleteRequest {

    @NotNull
    private Long dmId;

    @NotNull
    private Long userId;
}
