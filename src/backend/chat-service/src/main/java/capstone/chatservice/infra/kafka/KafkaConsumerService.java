package capstone.chatservice.infra.kafka;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.dto.response.ServerMessageCreateResponse;
import capstone.chatservice.domain.server.dto.response.ServerMessageDeleteResponse;
import capstone.chatservice.domain.server.dto.response.ServerMessageModifyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.server-chat}", groupId = "${spring.kafka.consumer.group-id.server-chat}", containerFactory = "serverChatListenerContainerFactory")
    public void serverChatListener(ServerMessageDto messageDto) {
        String messageType = messageDto.getType();
        Long serverId = messageDto.getServerId();
        if (messageType.equals("send")) {
            ServerMessageCreateResponse createResponse = ServerMessageCreateResponse.from(messageDto);
            messagingTemplate.convertAndSend("/topic/server/" + serverId, createResponse);
        } else if (messageType.equals("modify")) {
            ServerMessageModifyResponse modifyResponse = ServerMessageModifyResponse.from(messageDto);
            messagingTemplate.convertAndSend("/topic/server/" + serverId, modifyResponse);
        } else if (messageType.equals("delete")) {
            ServerMessageDeleteResponse deleteResponse = ServerMessageDeleteResponse.from(messageDto);
            messagingTemplate.convertAndSend("/topic/server/" + serverId, deleteResponse);
        }
    }
}