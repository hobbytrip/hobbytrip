package capstone.chatservice.infra.kafka.producer.chat;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.infra.kafka.producer.chat.event.ServerChatEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatEventProducer {

    @Value("${spring.kafka.topic.server-chat}")
    private String serverChatTopic;

    @Value("${spring.kafka.topic.direct-chat}")
    private String directChatTopic;

    @Value("${spring.kafka.topic.emoji-chat}")
    private String emojiChatTopic;

    @Value("${spring.kafka.topic.forum-chat}")
    private String forumChatTopic;


    private final KafkaTemplate<String, ServerChatEvent> serverChatKafkaTemplate;
    private final KafkaTemplate<String, DirectMessageDto> direcetChatKafkaTemplate;
    private final KafkaTemplate<String, EmojiDto> emojiChatKafkaTemplate;
    private final KafkaTemplate<String, ForumMessageDto> forumChatKafkaTemplate;

    public void sendToServerChatTopic(ServerChatEvent messageDto) {
        serverChatKafkaTemplate.send(serverChatTopic, messageDto);
    }

    public void sendToDirectChatTopic(DirectMessageDto directMessageDto) {
        direcetChatKafkaTemplate.send(directChatTopic, directMessageDto);
    }

    public void sendToEmojiChatTopic(EmojiDto emojiDto) {
        emojiChatKafkaTemplate.send(emojiChatTopic, emojiDto);
    }

    public void sendToForumChatTopic(ForumMessageDto forumMessageDto) {
        forumChatKafkaTemplate.send(forumChatTopic, forumMessageDto);
    }
}