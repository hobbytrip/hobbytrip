package capstone.communityservice.domain.server.dto;

import capstone.communityservice.domain.server.entity.ServerUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class ServerUserResponseDto {

    private Long serverId;

    private Long userId;

    private String name;

    public static ServerUserResponseDto of(ServerUser findServerUser, Long serverId, Long userId) {
        return ServerUserResponseDto.builder()
                .serverId(serverId)
                .userId(userId)
                .name(findServerUser.getName())
                .build();
    }
}
