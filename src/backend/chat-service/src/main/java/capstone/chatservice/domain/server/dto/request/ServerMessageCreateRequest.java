package capstone.chatservice.domain.server.dto.request;

import capstone.chatservice.domain.file.domain.UploadFile;
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
    private String type;
    private String writer;
    private String content;
    private List<UploadFile> files = null;
}