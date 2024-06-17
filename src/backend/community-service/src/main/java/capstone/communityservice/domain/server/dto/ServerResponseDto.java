package capstone.communityservice.domain.server.dto;

import capstone.communityservice.domain.server.entity.Server;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ServerResponseDto {

    private Long serverId;

    private Long managerId;

    private String profile;

    private String description;

    private String name;

    private boolean open;

    public static ServerResponseDto of(Server server){
        return ServerResponseDto.builder()
                .serverId(server.getId())
                .managerId(server.getManagerId())
                .profile(server.getProfile())
                .name(server.getName())
                .open(server.isOpen())
                .description(server.getDescription())
                .build();
    }
}
