package capstone.communityservice.domain.channel.service;

import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChannelCommandService {
    private final ChannelRepository channelRepository;

    public void save(Channel channel){
        channelRepository.save(channel);
    }
}
