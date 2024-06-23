package capstone.chatservice.infra.kafka.consumer.community;

import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.infra.kafka.consumer.community.dto.CommunityCategoryEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryEventConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.community-category-event}", groupId = "${spring.kafka.consumer.group-id.category-event}", containerFactory = "categoryEventListenerContainerFactory")
    public void categoryEventListener(CommunityCategoryEventDto categoryEventDto) {
        Long serverId = categoryEventDto.getServerId();
        messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(categoryEventDto));
    }
}
