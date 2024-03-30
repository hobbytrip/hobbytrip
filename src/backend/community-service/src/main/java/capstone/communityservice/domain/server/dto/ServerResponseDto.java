package capstone.communityservice.domain.server.dto;

import capstone.communityservice.domain.server.entity.Server;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ServerResponseDto {

    @NotNull
    private Long serverId;

    @NotNull
    private Long managerId;

    @NotBlank
    private String profile;

    @NotBlank
    private String name;

    public static ServerResponseDto of(Server server){
        return ServerResponseDto.builder()
                .serverId(server.getId())
                .managerId(server.getManagerId())
                .profile(server.getProfile())
                .name(server.getName())
                .build();
    }
}
