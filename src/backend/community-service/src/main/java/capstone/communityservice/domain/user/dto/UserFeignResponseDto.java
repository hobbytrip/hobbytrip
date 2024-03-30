package capstone.communityservice.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserFeignResponseDto {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String profile;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
