package capstone.communityservice.domain.dm.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DmCreateRequestDto {
    private List<Long> userIds;
}
