package capstone.chatservice.infra.kafka.producer.alarm.dto;

import capstone.chatservice.domain.dm.dto.request.DirectMessageCreateRequest;
import java.util.List;
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
    private String writer;
    private String profileImage;
    private AlarmType alarmType;
    private List<Long> receiverIds;

    public static DmAlarmEventDto from(DirectMessageCreateRequest createRequest) {
        return new DmAlarmEventDto(
                createRequest.getUserId(),
                createRequest.getDmRoomId(),
                createRequest.getContent(),
                createRequest.getWriter(),
                createRequest.getProfileImage(),
                AlarmType.DM,
                createRequest.getReceiverIds()
        );
    }
}
