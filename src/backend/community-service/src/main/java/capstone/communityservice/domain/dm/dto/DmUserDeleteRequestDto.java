package capstone.communityservice.domain.dm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DmUserDeleteRequestDto {

    @NotNull
    private Long dmId;

    @NotNull
    private Long userId;
}
