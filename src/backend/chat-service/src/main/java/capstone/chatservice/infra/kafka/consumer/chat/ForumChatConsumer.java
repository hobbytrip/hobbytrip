package capstone.chatservice.infra.kafka.consumer.chat;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.response.ForumMessageCreateResponse;
import capstone.chatservice.domain.forum.dto.response.ForumMessageDeleteResponse;
import capstone.chatservice.domain.forum.dto.response.ForumMessageModifyResponse;
import capstone.chatservice.domain.forum.dto.response.ForumMessageTypingResponse;
import capstone.chatservice.global.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForumChatConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.forum-chat}", groupId = "${spring.kafka.consumer.group-id.forum-chat}", containerFactory = "forumChatListenerContainerFactory")
    public void forumChatListener(ForumMessageDto messageDto) {
        Long serverId = messageDto.getServerId();
        switch (messageDto.getActionType()) {
            case SEND -> {
                ForumMessageCreateResponse createResponse = ForumMessageCreateResponse.from(messageDto);
                messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(createResponse));
            }
            case MODIFY -> {
                ForumMessageModifyResponse modifyResponse = ForumMessageModifyResponse.from(messageDto);
                messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(modifyResponse));
            }
            case DELETE -> {
                ForumMessageDeleteResponse deleteResponse = ForumMessageDeleteResponse.from(messageDto);
                messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(deleteResponse));
            }
            case TYPING -> {
                ForumMessageTypingResponse typingResponse = ForumMessageTypingResponse.from(messageDto);
                messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(typingResponse));
            }
        }
    }
}
