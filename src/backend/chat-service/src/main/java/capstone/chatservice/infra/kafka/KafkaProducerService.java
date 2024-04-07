package capstone.chatservice.infra.kafka;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    @Value("${spring.kafka.topic.server-chat}")
    private String serverChatTopic;

    private final KafkaTemplate<String, ServerMessageDto> serverChatKafkaTemplate;

    public void sendToServerChatTopic(ServerMessageDto messageDto) {
        serverChatKafkaTemplate.send(serverChatTopic, messageDto);
    }
}