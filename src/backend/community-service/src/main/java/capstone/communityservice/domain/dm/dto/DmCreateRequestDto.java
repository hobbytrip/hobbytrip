package capstone.communityservice.domain.dm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class DmCreateRequestDto {

    @NotNull
    private List<Long> userIds;
}
