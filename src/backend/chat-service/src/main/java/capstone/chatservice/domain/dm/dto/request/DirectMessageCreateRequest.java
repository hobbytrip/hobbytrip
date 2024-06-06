package capstone.chatservice.domain.dm.dto.request;

import capstone.chatservice.domain.file.domain.UploadFile;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageCreateRequest {

    private Long dmRoomId;
    private Long userId;
    private Long parentId;
    private String profileImage;
    private String type;
    private String writer;
    private String content;
    private List<Long> receiverIds;
    private List<UploadFile> files = null;
}
