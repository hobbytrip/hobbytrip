package capstone.communityservice.global.external.dto;

import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
public class ServerUserStateResponseDto {
    Map<Long, String> connectionStates;
    Map<String, Set<String>> channelStates;
}
