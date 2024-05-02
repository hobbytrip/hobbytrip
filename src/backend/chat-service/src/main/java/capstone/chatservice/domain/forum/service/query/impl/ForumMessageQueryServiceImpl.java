package capstone.chatservice.domain.forum.service.query.impl;

import capstone.chatservice.domain.emoji.domain.Emoji;
import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.emoji.repository.EmojiRepository;
import capstone.chatservice.domain.forum.domain.ForumMessage;
import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.repository.ForumMessageRepository;
import capstone.chatservice.domain.forum.service.query.ForumMessageQueryService;
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
public class ForumMessageQueryServiceImpl implements ForumMessageQueryService {

    private final EmojiRepository emojiRepository;
    private final ForumMessageRepository forumMessageRepository;

    @Override
    public Page<ForumMessageDto> getMessages(Long forumId, int page, int size) {
        Page<ForumMessageDto> messageDtos = messagesToMessageDtos("message", forumId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForMessages(messageIds);
        Map<Long, Long> messageCounts = getMessageCounts(messageIds);
        for (ForumMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
            messageDto.setCount(messageCounts.getOrDefault(messageDto.getMessageId(), 0L));
        }

        return messageDtos;
    }

    @Override
    public Page<ForumMessageDto> getComments(Long parentId, int page, int size) {
        Page<ForumMessageDto> messageDtos = messagesToMessageDtos("comment", parentId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForMessages(messageIds);
        for (ForumMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
        }

        return messageDtos;
    }

    private Page<ForumMessageDto> messagesToMessageDtos(String type, Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ForumMessage> messages = null;
        if (type.equals("message")) {
            messages = forumMessageRepository.findByForumIdAndIsDeletedAndParentId(id, pageable);
        } else if (type.equals("comment")) {
            messages = forumMessageRepository.findByParentIdAndIsDeleted(id, pageable);
        }

        return (messages != null) ? messages.map(ForumMessageDto::from) : Page.empty(pageable);
    }

    private Map<Long, List<EmojiDto>> getEmojisForMessages(List<Long> messageIds) {
        List<Emoji> emojis = emojiRepository.findEmojisByForumMessageIds(messageIds);
        Map<Long, List<EmojiDto>> emojiMap = new HashMap<>();

        for (Long messageId : messageIds) {
            List<EmojiDto> emojiDtos = new ArrayList<>();
            for (Emoji emoji : emojis) {
                if (messageId.equals(emoji.getForumMessageId())) {
                    EmojiDto emojiDto = EmojiDto.from(emoji);
                    emojiDtos.add(emojiDto);
                }
            }
            emojiMap.put(messageId, emojiDtos);
        }
        return emojiMap;
    }

    private Map<Long, Long> getMessageCounts(List<Long> messageIds) {
        List<ForumMessage> messages = forumMessageRepository.countMessagesByParentIds(messageIds);
        Map<Long, Long> messageCounts = new HashMap<>();

        for (Long messageId : messageIds) {
            long count = 0L;
            for (ForumMessage message : messages) {
                if (message.getParentId().equals(messageId)) {
                    count += 1;
                }
            }
            messageCounts.put(messageId, count);
        }

        return messageCounts;
    }

    private List<Long> getMessageIds(Page<ForumMessageDto> messageDtos) {
        return messageDtos.getContent().stream()
                .map(ForumMessageDto::getMessageId)
                .collect(Collectors.toList());
    }
}
