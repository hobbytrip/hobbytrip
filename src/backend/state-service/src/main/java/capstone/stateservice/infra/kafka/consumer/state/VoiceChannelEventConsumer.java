package capstone.stateservice.infra.kafka.consumer.state;

import capstone.stateservice.domain.voicestate.service.command.VoiceStateCommandService;
import capstone.stateservice.infra.kafka.consumer.state.dto.VoiceChannelEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoiceChannelEventConsumer {

    private final VoiceStateCommandService voiceStateCommandService;

    @KafkaListener(topics = "${spring.kafka.topic.voice-connection-state-event}", groupId = "${spring.kafka.consumer.group-id.voice-connection-state-event}", containerFactory = "voiceChannelEventListenerContainerFactory")
    public void voiceChannelEventListener(VoiceChannelEventDto voiceChannelEventDto) {
        voiceStateCommandService.saveVoiceState(voiceChannelEventDto);
    }
}
