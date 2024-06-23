package capstone.chatservice.infra.kafka.consumer.community;

import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.infra.kafka.consumer.community.dto.CommunityForumEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForumEventConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.community-forum-event}", groupId = "${spring.kafka.consumer.group-id.forum-event}", containerFactory = "forumEventListenerContainerFactory")
    public void forumEventListener(CommunityForumEventDto forumEventDto) {
        Long serverId = forumEventDto.getServerId();
        messagingTemplate.convertAndSend("/topic/server/" + serverId, DataResponseDto.of(forumEventDto));
    }
}
