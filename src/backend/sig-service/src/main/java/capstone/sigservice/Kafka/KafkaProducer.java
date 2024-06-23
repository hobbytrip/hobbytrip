package capstone.sigservice.Kafka;




import capstone.sigservice.dto.UserLocationEventDto;
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

    @Value("${spring.kafka.topic.user-location-event}")
    private String userLocationEventTopic;

    private final KafkaTemplate<String, VoiceChannelEventDto> voiceConnectionStateKafkaTemplate;

    private final KafkaTemplate<String, UserLocationEventDto> userLocationEventKafkaTemplate;

    public void sendToVoiceConnectionStateTopic(VoiceChannelEventDto voiceChannelEventDto) {
        voiceConnectionStateKafkaTemplate.send(voiceConnectionStateTopic, voiceChannelEventDto);
    }
    public void sendToUserLocationEvent(UserLocationEventDto userLocationEventDto) {
        userLocationEventKafkaTemplate.send(userLocationEventTopic, userLocationEventDto);
    }
}