package capstone.stateservice.infra.kafka.consumer.location.dto;

import capstone.stateservice.domain.model.ChannelType;
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
    private ChannelType channelType;
}
