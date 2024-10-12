package capstone.chatservice.global.websocket;

import capstone.chatservice.global.common.JwtTokenHandler;
import capstone.chatservice.global.exception.Code;
import capstone.chatservice.global.exception.GlobalException;
import capstone.chatservice.infra.client.CommunityServiceClient;
import capstone.chatservice.infra.client.StateServiceClient;
import capstone.chatservice.infra.client.UserServerDmInfo;
import capstone.chatservice.infra.kafka.producer.state.StateEventProducer;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionState;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateEventDto;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateInfo;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionType;
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

    private final StateServiceClient stateClient;
    private final CommunityServiceClient communityClient;
    private final JwtTokenHandler jwtTokenHandler;
    private final StateEventProducer stateEventProducer;

    private static final String AUTH_PREFIX = "Authorization";
    private static final String USER_ID = "userId";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        validateTokenOnConnect(headerAccessor);
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            Long userId = sendConnectionStateInfo(headerAccessor);
            sendConnectionStateEvent(userId);
        }

        if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
            Long userId = saveDisconnectionState(headerAccessor);
            if (userId != null) {
                sendDisConnectionStateEvent(userId);
            }
        }
    }

    private void validateTokenOnConnect(StompHeaderAccessor headerAccessor) {
        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            if (!jwtTokenHandler.validateToken(
                    Objects.requireNonNull(headerAccessor.getFirstNativeHeader(AUTH_PREFIX)))) {
                throw new GlobalException(Code.UNAUTHORIZED);
            }
        }
    }

    private Long sendConnectionStateInfo(StompHeaderAccessor headerAccessor) {
        Long userId = Long.parseLong(Objects.requireNonNull(headerAccessor.getFirstNativeHeader(USER_ID)));
        String sessionId = headerAccessor.getSessionId();
        ConnectionStateInfo connectionStateInfo = ConnectionStateInfo.builder()
                .userId(userId)
                .sessionId(sessionId)
                .type(ConnectionType.CONNECT)
                .state(ConnectionState.ONLINE)
                .build();
        stateEventProducer.sendToConnectionStateInfoTopic(connectionStateInfo);
        return userId;
    }

    private void sendConnectionStateEvent(Long userId) {
        UserServerDmInfo ids = communityClient.getServerIdsAndRoomIds(userId);
        ConnectionStateEventDto connectionEventDto = ConnectionStateEventDto.builder()
                .userId(userId)
                .type(ConnectionType.CONNECT)
                .state(ConnectionState.ONLINE)
                .serverIds(ids.getServerIds())
                .roomIds(ids.getDmIds())
                .build();
        stateEventProducer.sendToConnectionStateEventTopic(connectionEventDto);
    }

    private Long saveDisconnectionState(StompHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        ConnectionStateInfo connectionStateInfo = ConnectionStateInfo.builder()
                .sessionId(sessionId)
                .type(ConnectionType.DISCONNECT)
                .state(ConnectionState.OFFLINE)
                .build();

        return stateClient.saveUserConnectionState(connectionStateInfo).getData();
    }

    private void sendDisConnectionStateEvent(Long userId) {
        UserServerDmInfo ids = communityClient.getServerIdsAndRoomIds(userId);
        ConnectionStateEventDto connectionEventDto = ConnectionStateEventDto.builder()
                .userId(userId)
                .type(ConnectionType.DISCONNECT)
                .state(ConnectionState.OFFLINE)
                .serverIds(ids.getServerIds())
                .roomIds(ids.getDmIds())
                .build();
        stateEventProducer.sendToConnectionStateEventTopic(connectionEventDto);
    }
}