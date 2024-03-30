package capstone.communityservice.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequestEmailDto {

    @NotBlank
    private String email;
}
