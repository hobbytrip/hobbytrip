package capstone.chatservice.infra.kafka.producer.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DmAlarmEventDto {

    private Long userId;
    private Long dmRoomId;
    private String content;
    private AlarmType alarmType;
}
