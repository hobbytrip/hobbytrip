package capstone.communityservice.domain.dm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DmDeleteProfileRequestDto {

    @NotNull
    private Long dmId;

    @NotBlank
    private String profile;
}
