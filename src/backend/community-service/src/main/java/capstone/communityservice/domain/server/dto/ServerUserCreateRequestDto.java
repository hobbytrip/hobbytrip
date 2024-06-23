package capstone.communityservice.domain.server.dto;

import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
public class ServerUserCreateRequestDto {

    @NotNull
    private Server server;

    @NotNull
    private User user;

}
