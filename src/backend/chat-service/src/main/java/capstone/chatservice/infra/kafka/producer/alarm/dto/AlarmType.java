package capstone.chatservice.infra.kafka.producer.alarm.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {

    DM("DM-ALARM"), SERVER("SERVER-ALARM");

    private final String type;
}
