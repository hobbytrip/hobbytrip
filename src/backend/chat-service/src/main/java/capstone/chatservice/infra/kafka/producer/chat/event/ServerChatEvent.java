package capstone.chatservice.infra.kafka.producer.chat.event;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "serverChatEvent")
public class ServerChatEvent {

    @Transient
    public static final String SEQUENCE_NAME = "server_chat_event_sequence";

    @Id
    private Long eventId;

    private EventSentType eventSentType;

    private String uuid;

    private boolean isProcessed;

    private Long messageId;
    private Long serverId;
    private Long channelId;
    private Long userId;
    private Long parentId;
    private Long count;
    private String profileImage;
    private String writer;
    private String content;
    private boolean isDeleted;
    private ChatType chatType;
    private ActionType actionType;
    private List<UploadFile> files;
    private List<EmojiDto> emojis;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void generateSequence(Long eventId) {
        this.eventId = eventId;
    }

    @Builder
    public ServerChatEvent(String uuid, Long messageId,
                           Long serverId, Long channelId,
                           Long userId, Long parentId,
                           Long count, String profileImage,
                           String writer, String content,
                           ActionType actionType, boolean isDeleted,
                           List<EmojiDto> emojis, List<UploadFile> files,
                           ChatType chatType, LocalDateTime createdAt,
                           LocalDateTime modifiedAt, EventSentType eventSentType) {

        this.uuid = uuid;
        this.messageId = messageId;
        this.serverId = serverId;
        this.channelId = channelId;
        this.userId = userId;
        this.parentId = parentId;
        this.count = count;
        this.profileImage = profileImage;
        this.writer = writer;
        this.content = content;
        this.files = files;
        this.chatType = chatType;
        this.actionType = actionType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
        this.emojis = emojis;
        this.eventSentType = eventSentType;
    }

    public void changeEventSentType(EventSentType eventSentType) {
        this.eventSentType = eventSentType;
    }

    public void markAsProcessed() {
        this.isProcessed = true;
    }
}