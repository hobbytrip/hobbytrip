package capstone.chatservice.domain.server.service.query.impl;

import capstone.chatservice.domain.emoji.domain.Emoji;
import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.emoji.repository.EmojiRepository;
import capstone.chatservice.domain.server.domain.ServerMessage;
import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.repository.ServerMessageRepository;
import capstone.chatservice.domain.server.service.query.ServerMessageQueryService;
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
public class ServerMessageQueryServiceImpl implements ServerMessageQueryService {

    private final EmojiRepository emojiRepository;
    private final ServerMessageRepository messageRepository;

    @Override
    public Page<ServerMessageDto> getMessages(Long channelId, int page, int size) {
        Page<ServerMessageDto> messageDtos = messagesToMessageDtos("message", channelId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForMessages(messageIds);
        Map<Long, Long> commentCount = getCommentCountForMessages(messageIds);
        for (ServerMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
            messageDto.setCount(commentCount.getOrDefault(messageDto.getMessageId(), 0L));
        }

        return messageDtos;
    }

    @Override
    public Page<ServerMessageDto> getComments(Long parentId, int page, int size) {
        Page<ServerMessageDto> messageDtos = messagesToMessageDtos("comment", parentId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForMessages(messageIds);
        for (ServerMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
        }

        return messageDtos;
    }

    private Page<ServerMessageDto> messagesToMessageDtos(String type, Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ServerMessage> messages = null;
        if (type.equals("message")) {
            messages = messageRepository.findByChannelIdAndIsDeletedAndParentId(id, pageable);
        } else if (type.equals("comment")) {
            messages = messageRepository.findByParentIdAndIsDeleted(id, pageable);
        }

        return (messages != null) ? messages.map(ServerMessageDto::from) : Page.empty(pageable);
    }

    private Map<Long, List<EmojiDto>> getEmojisForMessages(List<Long> messageIds) {
        List<Emoji> emojis = emojiRepository.findEmojisByServerMessageIds(messageIds);
        Map<Long, List<EmojiDto>> emojiMap = new HashMap<>();

        for (Long messageId : messageIds) {
            List<EmojiDto> emojiDtos = new ArrayList<>();
            for (Emoji emoji : emojis) {
                if (messageId.equals(emoji.getServerMessageId())) {
                    EmojiDto emojiDto = EmojiDto.from(emoji);
                    emojiDtos.add(emojiDto);
                }
            }
            emojiMap.put(messageId, emojiDtos);
        }
        return emojiMap;
    }

    private Map<Long, Long> getCommentCountForMessages(List<Long> messageIds) {
        List<ServerMessage> messages = messageRepository.findCommentCountByParentIdsAndIsDeleted(messageIds);
        Map<Long, Long> messageCounts = new HashMap<>();

        for (Long messageId : messageIds) {
            long count = 0L;
            for (ServerMessage message : messages) {
                if (message.getParentId().equals(messageId)) {
                    count += 1;
                }
            }
            messageCounts.put(messageId, count);
        }

        return messageCounts;
    }

    private List<Long> getMessageIds(Page<ServerMessageDto> messageDtos) {
        return messageDtos.getContent().stream()
                .map(ServerMessageDto::getMessageId)
                .collect(Collectors.toList());
    }
}
