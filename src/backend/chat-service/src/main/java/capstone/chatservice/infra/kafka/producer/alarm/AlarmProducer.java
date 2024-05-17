package capstone.chatservice.infra.kafka.producer.alarm;

import capstone.chatservice.infra.kafka.producer.alarm.dto.DmAlarmEventDto;
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


    private final KafkaTemplate<String, DmAlarmEventDto> dmAlarmEventKafkaTemplate;

    public void sendToDmAlarmEventTopic(DmAlarmEventDto dmAlarmEventDto) {
        dmAlarmEventKafkaTemplate.send(dmAlarmEventTopic, dmAlarmEventDto);
    }
}
