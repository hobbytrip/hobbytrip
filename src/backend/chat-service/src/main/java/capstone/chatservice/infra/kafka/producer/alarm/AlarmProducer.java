package capstone.chatservice.infra.kafka.producer.alarm;

import capstone.chatservice.infra.kafka.producer.alarm.dto.DmAlarmEventDto;
import capstone.chatservice.infra.kafka.producer.alarm.dto.ServerAlarmEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmProducer {

    @Value("${spring.kafka.topic.dm-alarm-event}")
    private String dmAlarmEventTopic;

    @Value("${spring.kafka.topic.server-alarm-event}")
    private String serverAlarmEventTopic;

    private final KafkaTemplate<String, DmAlarmEventDto> dmAlarmEventKafkaTemplate;
    private final KafkaTemplate<String, ServerAlarmEventDto> serverAlarmEventKafkaTemplate;

    public void sendToDmAlarmEventTopic(DmAlarmEventDto dmAlarmEventDto) {
        dmAlarmEventKafkaTemplate.send(dmAlarmEventTopic, dmAlarmEventDto);
    }

    public void sendToServerAlarmEventTopic(ServerAlarmEventDto serverAlarmEventDto) {
        serverAlarmEventKafkaTemplate.send(serverAlarmEventTopic, serverAlarmEventDto);
    }
}
