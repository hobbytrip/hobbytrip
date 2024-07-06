package capstone.communityservice.domain.user.dto.response;

import capstone.communityservice.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserResponse {

    @NotNull
    private Long id;

    @NotNull
    private Long originalId;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String profile;

    public static UserResponse of(User user){
        return UserResponse.builder()
                .id(user.getId())
                .originalId(user.getOriginalId())
                .email(user.getEmail())
                .name(user.getName())
                .profile(user.getProfile())
                .build();
    }
}
