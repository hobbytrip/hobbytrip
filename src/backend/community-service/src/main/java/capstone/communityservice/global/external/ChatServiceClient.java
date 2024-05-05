package capstone.communityservice.global.external;

import capstone.communityservice.global.external.dto.ServerMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("chat-service")
public interface ChatServiceClient {

    @GetMapping("/server/messages/channel")
    Page<ServerMessageDto> getMessages(@RequestParam(value = "channelId") Long channelId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "30") int size);
}
