package capstone.chatservice.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "community-service")
public interface CommunityServiceClient {

    @GetMapping("/feign/{userId}")
    UserServerDmInfo getServerIdsAndRoomIds(@PathVariable(value = "userId") Long userId);
}