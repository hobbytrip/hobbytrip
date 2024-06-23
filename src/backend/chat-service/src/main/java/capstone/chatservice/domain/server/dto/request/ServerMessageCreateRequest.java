package capstone.chatservice.domain.server.dto.request;

import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.infra.kafka.producer.alarm.dto.MentionType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerMessageCreateRequest {

    private Long serverId;
    private Long channelId;
    private Long userId;
    private Long parentId;
    private String profileImage;
    private String writer;
    private String content;
    private MentionType mentionType;
    private List<Long> receiverIds;
    private List<UploadFile> files = null;
}