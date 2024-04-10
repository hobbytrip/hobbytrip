package capstone.chatservice.domain.dm.service;

import capstone.chatservice.domain.dm.domain.DirectMessage;
import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.dto.request.DirectMessageCreateRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageDeleteRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageModifyRequest;
import capstone.chatservice.domain.dm.repository.DirectMessageRepository;
import capstone.chatservice.domain.emoji.domain.Emoji;
import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.emoji.repository.EmojiRepository;
import capstone.chatservice.global.util.SequenceGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final SequenceGenerator sequenceGenerator;
    private final EmojiRepository emojiRepository;
    private final DirectMessageRepository messageRepository;

    @Transactional
    public DirectMessageDto save(DirectMessageCreateRequest createRequest) {
        DirectMessage directMessage = DirectMessage.builder()
                .dmRoomId(createRequest.getDmRoomId())
                .parentId(createRequest.getParentId())
                .userId(createRequest.getUserId())
                .content(createRequest.getContent())
                .writer(createRequest.getWriter())
                .type(createRequest.getType())
                .profileImage(createRequest.getProfileImage())
                .build();

        directMessage.generateSequence(sequenceGenerator.generateSequence(DirectMessage.SEQUENCE_NAME));

        return DirectMessageDto.from(messageRepository.save(directMessage));
    }

    @Transactional
    public DirectMessageDto modify(DirectMessageModifyRequest modifyRequest) {
        DirectMessage directMessage = messageRepository.findById(modifyRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("no message"));

        directMessage.modify(modifyRequest.getType(), modifyRequest.getContent());

        return DirectMessageDto.from(messageRepository.save(directMessage));
    }

    @Transactional
    public DirectMessageDto delete(DirectMessageDeleteRequest deleteRequest) {
        DirectMessage directMessage = messageRepository.findById(deleteRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException(" no message"));

        directMessage.delete(deleteRequest.getType());

        return DirectMessageDto.from(messageRepository.save(directMessage));
    }

    public Page<DirectMessageDto> getDirectMessages(Long roomId, int page, int size) {
        Page<DirectMessageDto> messageDtos = messagesToMessageDtos("message", roomId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForDirectMessages(messageIds);
        Map<Long, Long> messageCounts = getMessageCounts(messageIds);
        for (DirectMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
            messageDto.setCount(messageCounts.getOrDefault(messageDto.getMessageId(), 0L));
        }

        return messageDtos;
    }

    public Page<DirectMessageDto> getComments(Long parentId, int page, int size) {
        Page<DirectMessageDto> messageDtos = messagesToMessageDtos("comment", parentId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForDirectMessages(messageIds);
        for (DirectMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
        }

        return messageDtos;
    }

    public Page<DirectMessageDto> messagesToMessageDtos(String type, Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<DirectMessage> messages = null;
        if (type.equals("message")) {
            messages = messageRepository.findByDmRoomIdAndIsDeletedAndParentId(id, pageable);
        } else if (type.equals("comment")) {
            messages = messageRepository.findByParentIdAndIsDeleted(id, pageable);
        }

        return (messages != null) ? messages.map(DirectMessageDto::from) : Page.empty(pageable);
    }

    public Map<Long, List<EmojiDto>> getEmojisForDirectMessages(List<Long> messageIds) {
        List<Emoji> emojis = emojiRepository.findEmojisByDirectMessageIds(messageIds);
        Map<Long, List<EmojiDto>> emojiMap = new HashMap<>();

        for (Long messageId : messageIds) {
            List<EmojiDto> emojiDtos = new ArrayList<>();
            for (Emoji emoji : emojis) {
                if (messageId.equals(emoji.getDirectMessageId())) {
                    EmojiDto emojiDto = EmojiDto.from(emoji);
                    emojiDtos.add(emojiDto);
                }
            }
            emojiMap.put(messageId, emojiDtos);
        }
        return emojiMap;
    }

    public Map<Long, Long> getMessageCounts(List<Long> messageIds) {
        List<DirectMessage> messages = messageRepository.countMessagesByParentIds(messageIds);
        Map<Long, Long> messageCounts = new HashMap<>();

        for (Long messageId : messageIds) {
            long count = 0L;
            for (DirectMessage message : messages) {
                if (message.getParentId().equals(messageId)) {
                    count += 1;
                }
            }
            messageCounts.put(messageId, count);
        }

        return messageCounts;
    }

    public List<Long> getMessageIds(Page<DirectMessageDto> messageDtos) {
        return messageDtos.getContent().stream()
                .map(DirectMessageDto::getMessageId)
                .collect(Collectors.toList());
    }
}
