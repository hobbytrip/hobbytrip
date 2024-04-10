package capstone.chatservice.domain.dm.dto;

import capstone.chatservice.domain.dm.domain.DirectMessage;
import capstone.chatservice.domain.emoji.dto.EmojiDto;
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
public class DirectMessageDto {

    private Long messageId;
    private Long parentId;
    private Long dmRoomId;
    private Long userId;
    private Long count;
    private String profileImage;
    private String type;
    private String writer;
    private String content;
    private boolean isDeleted;
    private List<String> files;
    private List<EmojiDto> emojis;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static DirectMessageDto from(DirectMessage message) {
        return DirectMessageDto.builder()
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
                .modifiedAt(message.getModifiedAt())
                .build();
    }
}
