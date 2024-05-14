package capstone.chatservice.infra.kafka.consumer;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.dto.response.DirectMessageCreateResponse;
import capstone.chatservice.domain.dm.dto.response.DirectMessageDeleteResponse;
import capstone.chatservice.domain.dm.dto.response.DirectMessageModifyResponse;
import capstone.chatservice.domain.dm.dto.response.DirectMessageTypingResponse;
import capstone.chatservice.global.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectChatConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.direct-chat}", groupId = "${spring.kafka.consumer.group-id.direct-chat}", containerFactory = "directChatListenerContainerFactory")
    public void directChatListener(DirectMessageDto messageDto) {
        String messageType = messageDto.getType();
        Long dmRoomId = messageDto.getDmRoomId();
        switch (messageType) {
            case "send" -> {
                DirectMessageCreateResponse createResponse = DirectMessageCreateResponse.from(messageDto);
                messagingTemplate.convertAndSend("/topic/direct/" + dmRoomId, DataResponseDto.of(createResponse));
            }
            case "modify" -> {
                DirectMessageModifyResponse modifyResponse = DirectMessageModifyResponse.from(messageDto);
                messagingTemplate.convertAndSend("/topic/direct/" + dmRoomId, DataResponseDto.of(modifyResponse));
            }
            case "delete" -> {
                DirectMessageDeleteResponse deleteResponse = DirectMessageDeleteResponse.from(messageDto);
                messagingTemplate.convertAndSend("/topic/direct/" + dmRoomId, DataResponseDto.of(deleteResponse));
            }
            case "typing" -> {
                DirectMessageTypingResponse typingResponse = DirectMessageTypingResponse.from(messageDto);
                messagingTemplate.convertAndSend("/topic/direct/" + dmRoomId, DataResponseDto.of(typingResponse));
            }
        }
    }
}