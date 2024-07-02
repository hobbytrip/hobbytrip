package capstone.communityservice.domain.dm.dto.response;

import capstone.communityservice.domain.dm.entity.Dm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DmResponse {

    @NotNull
    private Long dmId;

    @NotBlank
    private String name;

    private String profile;

    public static DmResponse of(Dm dm) {
        return DmResponse.builder()
                .dmId(dm.getId())
                .name(dm.getName())
                .profile(dm.getProfile())
                .build();
    }
}
