package capstone.chatservice.infra.kafka.producer.alarm.dto;

import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerAlarmEventDto {

    private Long userId;
    private Long serverId;
    private String writer;
    private String content;
    private String profileImage;
    private List<Long> receiverIds;
    private AlarmType alarmType;
    private MentionType mentionType;

    public static ServerAlarmEventDto from(ServerMessageCreateRequest createRequest) {
        return new ServerAlarmEventDto(
                createRequest.getUserId(),
                createRequest.getServerId(),
                createRequest.getWriter(),
                createRequest.getContent(),
                createRequest.getProfileImage(),
                createRequest.getReceiverIds(),
                AlarmType.SERVER,
                createRequest.getMentionType()
        );
    }
}
