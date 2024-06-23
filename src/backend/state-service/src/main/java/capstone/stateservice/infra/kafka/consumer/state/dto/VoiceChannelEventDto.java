package capstone.stateservice.infra.kafka.consumer.state.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoiceChannelEventDto {

    private Long serverId;
    private Long channelId;
    private Long userId;
    private VoiceConnectionState voiceConnectionState;
}
