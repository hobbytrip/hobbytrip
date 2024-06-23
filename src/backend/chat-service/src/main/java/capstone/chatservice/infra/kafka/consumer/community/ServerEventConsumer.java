package capstone.chatservice.infra.kafka.consumer.community;

import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.infra.kafka.consumer.community.dto.CommunityServerEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServerEventConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.community-server-event}", groupId = "${spring.kafka.consumer.group-id.server-event}", containerFactory = "serverEventListenerContainerFactory")
    public void serverEventListener(CommunityServerEventDto serverEventDto) {
        Long serverId = serverEventDto.getServerId();
        messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(serverEventDto));
    }
}
