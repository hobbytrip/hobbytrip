package capstone.communityservice.domain.dm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DmDeleteRequestDto {

    @NotBlank
    private Long dmId;
}
