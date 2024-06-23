package capstone.chatservice.infra.kafka.consumer.community;

import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.infra.kafka.consumer.community.dto.CommunityDmEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DmEventConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.community-dm-event}", groupId = "${spring.kafka.consumer.group-id.dm-event}", containerFactory = "dmEventListenerContainerFactory")
    public void dmEventListener(CommunityDmEventDto dmEventDto) {
        Long dmId = dmEventDto.getDmId();
        messagingTemplate.convertAndSend("/topic/direct/" + dmId, DataResponseDto.of(dmEventDto));
    }
}
