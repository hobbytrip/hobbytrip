package capstone.chatservice.domain.emoji.contorller;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.emoji.dto.request.EmojiCreateRequest;
import capstone.chatservice.domain.emoji.dto.request.EmojiDeleteRequest;
import capstone.chatservice.domain.emoji.service.EmojiService;
import capstone.chatservice.infra.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmojiController {

    private final EmojiService emojiService;
    private final KafkaProducer producerService;

    @MessageMapping("/emoji/save")
    public void save(EmojiCreateRequest createRequest) {
        EmojiDto emojiDto = emojiService.save(createRequest);
        producerService.sendToEmojiChatTopic(emojiDto);
    }

    @MessageMapping("/emoji/delete")
    public void delete(EmojiDeleteRequest deleteRequest) {
        EmojiDto emojiDto = emojiService.delete(deleteRequest);
        producerService.sendToEmojiChatTopic(emojiDto);
    }
}