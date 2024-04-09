package capstone.chatservice.infra.kafka;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.server.dto.ServerMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topic.server-chat}")
    private String serverChatTopic;

    @Value("${spring.kafka.topic.emoji-chat}")
    private String emojiChatTopic;

    private final KafkaTemplate<String, ServerMessageDto> serverChatKafkaTemplate;
    private final KafkaTemplate<String, EmojiDto> emojiChatKafkaTemplate;

    public void sendToServerChatTopic(ServerMessageDto messageDto) {
        serverChatKafkaTemplate.send(serverChatTopic, messageDto);
    }

    public void sendToEmojiChatTopic(EmojiDto emojiDto) {
        emojiChatKafkaTemplate.send(emojiChatTopic, emojiDto);
    }
}