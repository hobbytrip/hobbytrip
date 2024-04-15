package capstone.chatservice.infra.kafka;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.response.ForumMessageCreateResponse;
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
        String messageType = messageDto.getType();
        Long serverId = messageDto.getServerId();
        switch (messageType) {
            case "send" -> {
                ForumMessageCreateResponse createResponse = ForumMessageCreateResponse.from(messageDto);
                messagingTemplate.convertAndSend("/topic/server/" + serverId, createResponse);
            }
        }
    }
}
