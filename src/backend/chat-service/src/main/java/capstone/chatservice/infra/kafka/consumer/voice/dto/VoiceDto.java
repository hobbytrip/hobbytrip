package capstone.chatservice.infra.kafka.consumer.voice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoiceDto {

    private Long userId;
    private Long serverId;
    private Long channelId;
    private VoiceConnectionState voiceConnectionState;
}
