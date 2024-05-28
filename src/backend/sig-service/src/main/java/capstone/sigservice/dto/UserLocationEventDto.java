package capstone.sigservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLocationEventDto {

    private Long userId;
    private Long serverId;
    private Long channelId;
}
