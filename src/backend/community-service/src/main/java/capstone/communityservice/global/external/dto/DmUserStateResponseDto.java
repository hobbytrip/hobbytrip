package capstone.communityservice.global.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class DmUserStateResponseDto{
    private Map<Long, String> connectionStates;
}
