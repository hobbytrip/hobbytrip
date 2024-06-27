package capstone.communityservice.domain.dm.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class DmJoinRequest {

    @NotNull
    private Long dmId;

    @NotNull
    private List<Long> userIds;

}
