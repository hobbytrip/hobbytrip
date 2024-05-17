package capstone.chatservice.infra.kafka.consumer.community;

import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.infra.kafka.consumer.community.dto.CommunityChannelEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelEventConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.community-channel-event}", groupId = "${spring.kafka.consumer.group-id.channel-event}", containerFactory = "channelEventListenerContainerFactory")
    public void channelEventListener(CommunityChannelEventDto channelEventDto) {
        Long serverId = channelEventDto.getServerId();
        messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(channelEventDto));
    }
}
