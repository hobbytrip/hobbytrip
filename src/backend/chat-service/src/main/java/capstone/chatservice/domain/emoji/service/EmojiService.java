package capstone.chatservice.domain.emoji.service;

import capstone.chatservice.domain.emoji.domain.Emoji;
import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.emoji.dto.request.EmojiCreateRequest;
import capstone.chatservice.domain.emoji.dto.request.EmojiDeleteRequest;
import capstone.chatservice.domain.emoji.repository.EmojiRepository;
import capstone.chatservice.global.util.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmojiService {

    private final EmojiRepository emojiRepository;
    private final SequenceGenerator sequenceGenerator;

    @Transactional
    public EmojiDto save(EmojiCreateRequest emojiCreateRequest) {
        Emoji emoji = Emoji.builder()
                .serverId(emojiCreateRequest.getServerId())
                .channelId(emojiCreateRequest.getChannelId())
                .dmId(emojiCreateRequest.getDmId())
                .serverMessageId(emojiCreateRequest.getServerMessageId())
                .forumMessageId(emojiCreateRequest.getForumMessageId())
                .directMessageId(emojiCreateRequest.getDirectMessageId())
                .userId(emojiCreateRequest.getUserId())
                .typeId(emojiCreateRequest.getTypeId())
                .type(emojiCreateRequest.getType())
                .build();

        emoji.generateSequence(sequenceGenerator.generateSequence(Emoji.SEQUENCE_NAME));

        return EmojiDto.from(emojiRepository.save(emoji));
    }

    @Transactional
    public EmojiDto delete(EmojiDeleteRequest deleteRequest) {
        Emoji emoji = emojiRepository.findById(deleteRequest.getEmojiId())
                .orElseThrow(() -> new RuntimeException("예외 발생"));

        EmojiDto emojiDto = EmojiDto.from(emoji);
        emojiDto.setType(deleteRequest.getType());

        emojiRepository.delete(emoji);

        return emojiDto;
    }
}