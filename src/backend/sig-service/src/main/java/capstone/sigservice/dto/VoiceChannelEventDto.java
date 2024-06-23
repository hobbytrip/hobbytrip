package capstone.sigservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoiceChannelEventDto {
    private Long serverId;
    private Long channelId;
    private Long userId;
    private VoiceConnectionState voiceConnectionState;



}
