package capstone.chatservice.domain.server.controller;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.service.query.ServerMessageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ServerMessageQueryController {

    private final ServerMessageQueryService queryService;

    @GetMapping("/api/chat/server/messages/channel")
    public Page<ServerMessageDto> getMessages(@RequestParam(value = "channelId") Long channelId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "30") int size) {

        return queryService.getMessages(channelId, page, size);
    }

    @GetMapping("/api/chat/server/comments/message")
    public Page<ServerMessageDto> getComments(@RequestParam(value = "parentId") Long parentId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "30") int size) {

        return queryService.getComments(parentId, page, size);
    }
}
