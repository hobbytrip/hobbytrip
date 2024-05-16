package capstone.sigservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoiceDto {
    private Long serverId;
    private Long channelId;
    private Long UserId;
    private enum voiceConnectionState{
        voiceJoin,voiceLeave
    }
}