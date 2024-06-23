package capstone.chatservice.domain.dm.service.query.impl;

import capstone.chatservice.domain.dm.domain.DirectMessage;
import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.repository.DirectMessageRepository;
import capstone.chatservice.domain.dm.service.query.DirectMessageQueryService;
import capstone.chatservice.domain.emoji.domain.Emoji;
import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.emoji.repository.EmojiRepository;
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

@Service
@RequiredArgsConstructor
public class DirectMessageQueryServiceImpl implements DirectMessageQueryService {

    private final EmojiRepository emojiRepository;
    private final DirectMessageRepository messageRepository;

    @Override
    public Page<DirectMessageDto> getDirectMessages(Long roomId, int page, int size) {
        Page<DirectMessageDto> messageDtos = messagesToMessageDtos("message", roomId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForDirectMessages(messageIds);
        Map<Long, Long> commentCount = getCommentCountForMessages(messageIds);
        for (DirectMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
            messageDto.setCount(commentCount.getOrDefault(messageDto.getMessageId(), 0L));
        }

        return messageDtos;
    }

    @Override
    public Page<DirectMessageDto> getComments(Long parentId, int page, int size) {
        Page<DirectMessageDto> messageDtos = messagesToMessageDtos("comment", parentId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForDirectMessages(messageIds);
        for (DirectMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
        }

        return messageDtos;
    }

    private Page<DirectMessageDto> messagesToMessageDtos(String type, Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<DirectMessage> messages = null;
        if (type.equals("message")) {
            messages = messageRepository.findByDmRoomIdAndIsDeletedAndParentId(id, pageable);
        } else if (type.equals("comment")) {
            messages = messageRepository.findByParentIdAndIsDeleted(id, pageable);
        }

        return (messages != null) ? messages.map(DirectMessageDto::from) : Page.empty(pageable);
    }

    private Map<Long, List<EmojiDto>> getEmojisForDirectMessages(List<Long> messageIds) {
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

    private Map<Long, Long> getCommentCountForMessages(List<Long> messageIds) {
        List<DirectMessage> messages = messageRepository.findCommentCountByParentIdsAndIsDeleted(messageIds);
        Map<Long, Long> commentCount = new HashMap<>();

        for (Long messageId : messageIds) {
            long count = 0L;
            for (DirectMessage message : messages) {
                if (message.getParentId().equals(messageId)) {
                    count += 1;
                }
            }
            commentCount.put(messageId, count);
        }

        return commentCount;
    }

    private List<Long> getMessageIds(Page<DirectMessageDto> messageDtos) {
        return messageDtos.getContent().stream()
                .map(DirectMessageDto::getMessageId)
                .collect(Collectors.toList());
    }
}
