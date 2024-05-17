package capstone.sigservice.controller;

import capstone.sigservice.dto.VoiceConnectionState;
import capstone.sigservice.dto.VoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaTestController {
    @Autowired
    private KafkaTemplate<String, VoiceDto> kafkaTemplate;
    @Value("${spring.kafka.topic.voice-connection-state-event}")
    private String voiceConnectionStateTopic;
    @GetMapping("/test")
    public void test(){
        VoiceDto voiceDto=new VoiceDto();

        voiceDto.setServerId(1L);
        voiceDto.setChannelId(1L);
        voiceDto.setUserId(1L);
        voiceDto.setVoiceConnectionState(VoiceConnectionState.VOICE_JOIN);
        kafkaTemplate.send(voiceConnectionStateTopic,voiceDto);
    }

}
