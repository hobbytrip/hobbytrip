package capstone.communityservice.domain.dm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DmUpdateProfileRequestDto {

    @NotNull
    private Long dmId;
}
