package capstone.communityservice.domain.dm.dto;

import capstone.communityservice.domain.dm.entity.Dm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DmResponseDto {

    @NotNull
    private Long dmId;

    @NotBlank
    private String name;

    private String profile;

    public static DmResponseDto of(Dm dm) {
        return DmResponseDto.builder()
                .dmId(dm.getId())
                .name(dm.getName())
                .profile(dm.getProfile())
                .build();
    }
}
