package capstone.chatservice.infra.websocket;

import capstone.chatservice.infra.client.CommunityServiceClient;
import capstone.chatservice.infra.client.StateServiceClient;
import capstone.chatservice.infra.client.UserServerDmInfo;
import capstone.chatservice.infra.kafka.KafkaProducer;
import capstone.chatservice.infra.kafka.dto.ConnectionStateEventDto;
import capstone.chatservice.infra.kafka.dto.ConnectionStateInfo;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketConnectionHandler implements ChannelInterceptor {

    private final KafkaProducer kafkaProducer;
    private final StateServiceClient stateClient;
    private final CommunityServiceClient communityClient;

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            Long userId = Long.parseLong(Objects.requireNonNull(headerAccessor.getFirstNativeHeader("userId")));
            String sessionId = headerAccessor.getSessionId();
            ConnectionStateInfo connectionStateInfo = ConnectionStateInfo.builder()
                    .userId(userId)
                    .sessionId(sessionId)
                    .type("CONNECT")
                    .state("online")
                    .build();
            kafkaProducer.sendToConnectionStateInfoTopic(connectionStateInfo);

            UserServerDmInfo ids = communityClient.getServerIdsAndRoomIds(userId);
            ConnectionStateEventDto connectionEventDto = ConnectionStateEventDto.builder()
                    .userId(userId)
                    .type("CONNECT")
                    .state("online")
                    .serverIds(ids.getServerIds())
                    .roomIds(ids.getRoomIds())
                    .build();
            kafkaProducer.sendToConnectionStateEventTopic(connectionEventDto);
        }

        if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
            String sessionId = headerAccessor.getSessionId();
            ConnectionStateInfo connectionStateInfo = ConnectionStateInfo.builder()
                    .sessionId(sessionId)
                    .type("DISCONNECT")
                    .state("offline")
                    .build();

            String checkUserId = stateClient.saveConnectionStateInfo(connectionStateInfo).getData();
            if (checkUserId != null) {
                Long userId = Long.parseLong(checkUserId);

                UserServerDmInfo ids = communityClient.getServerIdsAndRoomIds(userId);
                ConnectionStateEventDto connectionEventDto = ConnectionStateEventDto.builder()
                        .userId(userId)
                        .type("DISCONNECT")
                        .state("offline")
                        .serverIds(ids.getServerIds())
                        .roomIds(ids.getRoomIds())
                        .build();
                kafkaProducer.sendToConnectionStateEventTopic(connectionEventDto);
            }
        }
    }
}