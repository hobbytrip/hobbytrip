package capstone.sigservice.Kafka;




import capstone.sigservice.dto.VoiceChannelEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topic.voice-connection-state-event}")
    private String voiceConnectionStateTopic;

    private final KafkaTemplate<String, VoiceChannelEventDto> voiceConnectionStateKafkaTemplate;



    public void sendToVoiceConnectionStateTopic(VoiceChannelEventDto voiceChannelEventDto) {
        voiceConnectionStateKafkaTemplate.send(voiceConnectionStateTopic, voiceChannelEventDto);
    }
}