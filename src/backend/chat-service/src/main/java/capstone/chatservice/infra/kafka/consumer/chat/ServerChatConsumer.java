package capstone.chatservice.infra.kafka.consumer.chat;

import capstone.chatservice.domain.server.dto.response.ServerMessageCreateResponse;
import capstone.chatservice.domain.server.dto.response.ServerMessageDeleteResponse;
import capstone.chatservice.domain.server.dto.response.ServerMessageModifyResponse;
import capstone.chatservice.domain.server.dto.response.ServerMessageTypingResponse;
import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.infra.kafka.producer.chat.event.ServerChatEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServerChatConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.server-chat}", groupId = "${spring.kafka.consumer.group-id.server-chat}", containerFactory = "serverChatListenerContainerFactory")
    public void serverChatListener(ServerChatEvent chatEvent) {
        Long serverId = chatEvent.getServerId();

        switch (chatEvent.getActionType()) {
            case SEND -> {
                ServerMessageCreateResponse createResponse = ServerMessageCreateResponse.from(chatEvent);
                messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(createResponse));
            }
            case MODIFY -> {
                ServerMessageModifyResponse modifyResponse = ServerMessageModifyResponse.from(chatEvent);
                messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(modifyResponse));
            }
            case DELETE -> {
                ServerMessageDeleteResponse deleteResponse = ServerMessageDeleteResponse.from(chatEvent);
                messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(deleteResponse));
            }
            case TYPING -> {
                ServerMessageTypingResponse typingResponse = ServerMessageTypingResponse.from(chatEvent);
                messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(typingResponse));
            }
        }
    }
}