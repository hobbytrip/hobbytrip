package capstone.communityservice.domain.user.dto;

import capstone.communityservice.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserResponseDto {

    @NotNull
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String profile;

    public static UserResponseDto of(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .profile(user.getProfile())
                .build();
    }
}
