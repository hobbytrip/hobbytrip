package capstone.chatservice.infra.kafka.consumer.voice;

import capstone.chatservice.infra.kafka.consumer.voice.dto.VoiceConnectionState;
import capstone.chatservice.infra.kafka.consumer.voice.dto.VoiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoiceConnectionStateEventConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.voice-connection-state-event}", groupId = "${spring.kafka.consumer.group-id.voice-connection-state-event}", containerFactory = "voiceConnectionStateEventListenerContainerFactory")
    public void voiceConnectionStateEventListener(VoiceDto voiceDto) {
        if (voiceDto != null && (voiceDto.getVoiceConnectionState() == VoiceConnectionState.VOICE_JOIN
                || voiceDto.getVoiceConnectionState() == VoiceConnectionState.VOICE_LEAVE)) {

            messagingTemplate.convertAndSend("/topic/server/" + voiceDto.getServerId(), voiceDto);
        }
    }
}
