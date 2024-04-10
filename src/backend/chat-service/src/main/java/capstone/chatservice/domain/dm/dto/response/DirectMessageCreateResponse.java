package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import java.time.LocalDateTime;
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
public class DirectMessageCreateResponse {

    private Long messageId;
    private Long dmRoomId;
    private Long userId;
    private Long parentId;
    private String profileImage;
    private String type;
    private String writer;
    private String content;
    private boolean isDeleted;
    private List<String> files;
    private LocalDateTime createdAt;

    public static DirectMessageCreateResponse from(DirectMessageDto message) {
        return DirectMessageCreateResponse.builder()
                .messageId(message.getMessageId())
                .dmRoomId(message.getDmRoomId())
                .userId(message.getUserId())
                .parentId(message.getParentId())
                .profileImage(message.getProfileImage())
                .type(message.getType())
                .writer(message.getWriter())
                .content(message.getContent())
                .isDeleted(message.isDeleted())
                .files(message.getFiles())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
