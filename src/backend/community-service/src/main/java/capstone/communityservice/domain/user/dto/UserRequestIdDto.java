package capstone.communityservice.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRequestIdDto {

    @NotNull
    private Long originalId;
}
