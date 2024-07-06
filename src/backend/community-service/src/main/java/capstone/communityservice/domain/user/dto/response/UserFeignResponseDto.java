package capstone.communityservice.domain.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserFeignResponseDto {
    @NotNull
    private Long originalId;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String profile;

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }

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
