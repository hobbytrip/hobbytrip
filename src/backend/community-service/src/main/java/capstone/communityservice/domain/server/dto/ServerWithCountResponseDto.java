package capstone.communityservice.domain.server.dto;

import capstone.communityservice.domain.server.entity.Server;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class ServerWithCountResponseDto {

    @NotNull
    private Long serverId;

    @NotBlank
    private String serverName;

    @NotNull
    private int userCount;


    public static ServerWithCountResponseDto of(Server server) {
        return ServerWithCountResponseDto.builder()
                .serverId(server.getId())
                .serverName(server.getName())
                .userCount(server.getServerUsers().size())
                .build();
    }
}
