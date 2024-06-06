package capstone.chatservice.domain.forum.dto.request;

import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.domain.model.ForumCategory;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumMessageCreateRequest {

    private Long serverId;
    private Long forumId;
    private Long channelId;
    private Long userId;
    private Long parentId;
    private String profileImage;
    private String type;
    private String writer;
    private String content;
    private ForumCategory forumCategory;
    private List<UploadFile> files = null;
}
