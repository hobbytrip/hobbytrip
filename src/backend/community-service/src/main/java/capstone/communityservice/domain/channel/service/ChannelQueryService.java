package capstone.communityservice.domain.channel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelQueryService {

    private final ChannelCommandService channelCommandService;

    public void sendUserLocation(Long channelId, Long userId) {
        channelCommandService.sendUserLocEvent(channelId, userId);
    }
}
