package capstone.chatservice.infra.kafka.producer.chat.handler;

import capstone.chatservice.domain.server.dto.event.ServerChatCreateEvent;
import capstone.chatservice.domain.server.dto.event.ServerChatDeleteEvent;
import capstone.chatservice.domain.server.dto.event.ServerChatModifyEvent;
import capstone.chatservice.domain.server.dto.event.ServerChatTypingEvent;
import capstone.chatservice.global.util.SequenceGenerator;
import capstone.chatservice.infra.kafka.producer.chat.ChatEventProducer;
import capstone.chatservice.infra.kafka.producer.chat.event.EventSentType;
import capstone.chatservice.infra.kafka.producer.chat.event.ServerChatEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerChatEventHandler {

    private final SequenceGenerator sequenceGenerator;
    private final ChatEventProducer chatEventProducer;
    private final ServerChatEventRepository serverChatEventRepository;

    @TransactionalEventListener(classes = ServerChatCreateEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void serverChatCreateEventBeforeHandler(ServerChatCreateEvent chatCreateEvent) {
        ServerChatEvent serverChatEvent = createServerChatEvent(chatCreateEvent);
        serverChatEvent.generateSequence(sequenceGenerator.generateSequence(ServerChatEvent.SEQUENCE_NAME));
        serverChatEventRepository.save(serverChatEvent);
    }

    @Async
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = ServerChatCreateEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void serverChatCreateEventAfterHandler(ServerChatCreateEvent chatCreateEvent) {
        ServerChatEvent serverChatEvent = createServerChatEvent(chatCreateEvent);
        publishServerChatEvent(serverChatEvent);
    }

    @TransactionalEventListener(classes = ServerChatModifyEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void serverChatModifyEventBeforeHandler(ServerChatModifyEvent chatModifyEvent) {
        ServerChatEvent serverChatEvent = createServerChatEvent(chatModifyEvent);
        serverChatEvent.generateSequence(sequenceGenerator.generateSequence(ServerChatEvent.SEQUENCE_NAME));
        serverChatEventRepository.save(serverChatEvent);
    }

    @Async
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = ServerChatModifyEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void serverChatModifyEventAfterHandler(ServerChatModifyEvent chatModifyEvent) {
        ServerChatEvent serverChatEvent = createServerChatEvent(chatModifyEvent);
        publishServerChatEvent(serverChatEvent);
    }

    @TransactionalEventListener(classes = ServerChatDeleteEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void serverChatDeleteEventBeforeHandler(ServerChatDeleteEvent chatDeleteEvent) {
        ServerChatEvent serverChatEvent = createServerChatEvent(chatDeleteEvent);
        serverChatEvent.generateSequence(sequenceGenerator.generateSequence(ServerChatEvent.SEQUENCE_NAME));
        serverChatEventRepository.save(serverChatEvent);
    }

    @Async
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = ServerChatDeleteEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void serverChatDeleteEventAfterHandler(ServerChatDeleteEvent chatDeleteEvent) {
        ServerChatEvent serverChatEvent = createServerChatEvent(chatDeleteEvent);
        publishServerChatEvent(serverChatEvent);
    }

    @TransactionalEventListener(classes = ServerChatTypingEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void serverChatTypingEventBeforeHandler(ServerChatTypingEvent chatTypingEvent) {
        ServerChatEvent serverChatEvent = createServerChatEvent(chatTypingEvent);
        serverChatEvent.generateSequence(sequenceGenerator.generateSequence(ServerChatEvent.SEQUENCE_NAME));
        serverChatEventRepository.save(serverChatEvent);
    }

    @Async
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = ServerChatTypingEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void serverChatTypingEventAfterHandler(ServerChatTypingEvent chatTypingEvent) {
        ServerChatEvent serverChatEvent = createServerChatEvent(chatTypingEvent);
        publishServerChatEvent(serverChatEvent);
    }

    private void publishServerChatEvent(ServerChatEvent serverChatEvent) {
        ServerChatEvent chatEvent = serverChatEventRepository.findByUuid(serverChatEvent.getUuid())
                .orElseThrow(() -> new RuntimeException("no event"));

        try {
            chatEventProducer.sendToServerChatTopic(chatEvent);
            chatEvent.changeEventSentType(EventSentType.SEND_SUCCESS);
            serverChatEventRepository.save(chatEvent);
        } catch (Exception e) {
            chatEvent.changeEventSentType(EventSentType.SEND_FAIL);
            serverChatEventRepository.save(chatEvent);
        }
    }

    private ServerChatEvent createServerChatEvent(ServerChatCreateEvent chatCreateEvent) {
        return ServerChatEvent.builder()
                .uuid(chatCreateEvent.getUuid())
                .eventSentType(EventSentType.INIT)
                .messageId(chatCreateEvent.getMessageId())
                .serverId(chatCreateEvent.getServerId())
                .channelId(chatCreateEvent.getChannelId())
                .userId(chatCreateEvent.getUserId())
                .parentId(chatCreateEvent.getParentId())
                .count(chatCreateEvent.getCount())
                .profileImage(chatCreateEvent.getProfileImage())
                .writer(chatCreateEvent.getWriter())
                .content(chatCreateEvent.getContent())
                .isDeleted(chatCreateEvent.isDeleted())
                .chatType(chatCreateEvent.getChatType())
                .actionType(chatCreateEvent.getActionType())
                .files(chatCreateEvent.getFiles())
                .emojis(chatCreateEvent.getEmojis())
                .createdAt(chatCreateEvent.getCreatedAt())
                .modifiedAt(chatCreateEvent.getModifiedAt())
                .build();
    }

    private ServerChatEvent createServerChatEvent(ServerChatModifyEvent chatModifyEvent) {
        return ServerChatEvent.builder()
                .uuid(chatModifyEvent.getUuid())
                .eventSentType(EventSentType.INIT)
                .messageId(chatModifyEvent.getMessageId())
                .serverId(chatModifyEvent.getServerId())
                .content(chatModifyEvent.getContent())
                .chatType(chatModifyEvent.getChatType())
                .actionType(chatModifyEvent.getActionType())
                .createdAt(chatModifyEvent.getCreatedAt())
                .modifiedAt(chatModifyEvent.getModifiedAt())
                .build();
    }

    private ServerChatEvent createServerChatEvent(ServerChatDeleteEvent chatDeleteEvent) {
        return ServerChatEvent.builder()
                .uuid(chatDeleteEvent.getUuid())
                .eventSentType(EventSentType.INIT)
                .messageId(chatDeleteEvent.getMessageId())
                .chatType(chatDeleteEvent.getChatType())
                .actionType(chatDeleteEvent.getActionType())
                .build();
    }

    private ServerChatEvent createServerChatEvent(ServerChatTypingEvent chatTypingEvent) {
        return ServerChatEvent.builder()
                .uuid(chatTypingEvent.getUuid())
                .eventSentType(EventSentType.INIT)
                .chatType(chatTypingEvent.getChatType())
                .actionType(chatTypingEvent.getActionType())
                .build();
    }
}