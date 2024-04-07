package capstone.chatservice.domain.emoji.contorller;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.emoji.dto.request.EmojiCreateRequest;
import capstone.chatservice.domain.emoji.service.EmojiService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmojiController {

    private final EmojiService emojiService;

    @MessageMapping("/emoji/save")
    public void save(EmojiCreateRequest createRequest) {
        EmojiDto emoji = emojiService.save(createRequest);
    }

}