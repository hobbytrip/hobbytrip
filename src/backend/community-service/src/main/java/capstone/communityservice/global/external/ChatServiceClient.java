package capstone.communityservice.global.external;

import capstone.communityservice.domain.forum.dto.response.ForumChannelResponseDto;
import capstone.communityservice.global.external.dto.DmMessageDto;
import capstone.communityservice.global.external.dto.ForumMessageDto;
import capstone.communityservice.global.external.dto.ServerMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("chat-service")
public interface ChatServiceClient {

    @GetMapping("/feign/server/messages/channel")
    Page<ServerMessageDto> getServerMessages(
            @RequestParam(value = "channelId") Long channelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    );

    @GetMapping("/feign/direct/messages/room")
    Page<DmMessageDto> getDmMessages(
            @RequestParam(value = "roomId") Long roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    );

    @GetMapping("/feign/forum/messages/forum")
    Page<ForumMessageDto> getForumMessages(
            @RequestParam(value = "forumId") Long forumId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    );

    @GetMapping("/feign/forum/messages/count")
    ForumChannelResponseDto getForumsMessageCount(
            @RequestParam(value = "forumIds") List<Long> forumIds
    );

}
