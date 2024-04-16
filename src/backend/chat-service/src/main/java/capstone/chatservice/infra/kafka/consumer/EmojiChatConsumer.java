package capstone.chatservice.infra.kafka.consumer;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.emoji.dto.response.EmojiCreateResponse;
import capstone.chatservice.domain.emoji.dto.response.EmojiDeleteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmojiChatConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.emoji-chat}", groupId = "${spring.kafka.consumer.group-id.emoji-chat}", containerFactory = "emojiChatListenerContainerFactory")
    public void emojiChatListener(EmojiDto emojiDto) {
        String emojiType = emojiDto.getType();
        Long serverId = emojiDto.getServerId();
        Long dmId = emojiDto.getDmId();
        switch (emojiType) {
            case "save" -> {
                EmojiCreateResponse createResponse = EmojiCreateResponse.from(emojiDto);
                if (emojiDto.getServerId() > 0) {
                    messagingTemplate.convertAndSend("/topic/server/" + serverId, createResponse);
                } else {
                    messagingTemplate.convertAndSend("/topic/direct/" + dmId, createResponse);
                }
            }
            case "delete" -> {
                EmojiDeleteResponse deleteResponse = EmojiDeleteResponse.from(emojiDto);
                if (emojiDto.getServerId() > 0) {
                    messagingTemplate.convertAndSend("/topic/server/" + serverId, deleteResponse);
                } else {
                    messagingTemplate.convertAndSend("/topic/direct/" + dmId, deleteResponse);
                }
            }
        }
    }
}
