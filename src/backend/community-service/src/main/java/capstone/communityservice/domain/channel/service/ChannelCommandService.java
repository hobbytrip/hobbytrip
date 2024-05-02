package capstone.communityservice.domain.channel.service;

import capstone.communityservice.domain.category.exception.CategoryException;
import capstone.communityservice.domain.category.repository.CategoryRepository;
import capstone.communityservice.domain.channel.dto.ChannelCreateRequestDto;
import capstone.communityservice.domain.channel.dto.ChannelDeleteRequestDto;
import capstone.communityservice.domain.channel.dto.ChannelResponseDto;
import capstone.communityservice.domain.channel.dto.ChannelUpdateRequestDto;
import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.exception.ChannelException;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.server.service.ServerQueryService;
import capstone.communityservice.global.common.dto.kafka.CommunityChannelEventDto;
import capstone.communityservice.global.common.dto.kafka.CommunityDmEventDto;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChannelCommandService {
    private static final String channelKafkaTopic = "communityChannelEventTopic";

    private final KafkaTemplate<String, CommunityChannelEventDto> channelKafkaTemplate;

    private final ServerQueryService serverQueryService;

    private final ChannelRepository channelRepository;
    private final ServerRepository serverRepository;
    private final CategoryRepository categoryRepository;

    public ChannelResponseDto create(ChannelCreateRequestDto requestDto) {
        Server findServer = validateManagerInChannel(requestDto.getServerId(), requestDto.getUserId());

        validateCategory(requestDto.getCategoryId());

        Channel newChannel = channelRepository.save(createChannel(findServer, requestDto));

        channelKafkaTemplate.send(channelKafkaTopic, CommunityChannelEventDto.of("channel-create", newChannel, findServer.getId()));

        return ChannelResponseDto.of(newChannel);
    }

    public ChannelResponseDto update(ChannelUpdateRequestDto requestDto) {
        validateManagerInChannel(requestDto.getServerId(), requestDto.getUserId());
        validateCategory(requestDto.getCategoryId());

        Channel findChannel = validateChannel(requestDto.getChannelId());

        findChannel.modifyChannel(requestDto.getCategoryId(), requestDto.getName());

        channelKafkaTemplate.send(channelKafkaTopic, CommunityChannelEventDto.of("channel-update", findChannel, requestDto.getServerId()));

        return ChannelResponseDto.of(findChannel);
    }

    public void delete(ChannelDeleteRequestDto requestDto) {
        validateManagerInChannel(requestDto.getServerId(), requestDto.getUserId());

        Channel findChannel = validateChannel(requestDto.getChannelId());

        channelKafkaTemplate.send(channelKafkaTopic, CommunityChannelEventDto.of("channel-delete", findChannel, requestDto.getServerId()));

        channelRepository.delete(findChannel);
    }

    private Channel createChannel(Server findServer, ChannelCreateRequestDto requestDto){
        return Channel.of(
                findServer,
                requestDto.getCategoryId(),
                requestDto.getChannelType(),
                requestDto.getName()
        );
    }

    private Channel validateChannel(Long channelId){
        return channelRepository.findById(channelId)
                .orElseThrow(() ->
                        new ChannelException(Code.NOT_FOUND, "Channel Not Found")
                );
    }

    private Server validateManagerInChannel(Long serverId, Long userId) {
        Server findServer = this.validateExistServer(serverId);

        serverQueryService.validateManager(findServer.getManagerId(), userId);
        return findServer;
    }

    private Server validateExistServer(Long serverId){
        return serverRepository.findById(serverId)
                .orElseThrow(() ->
                        new ServerException(Code.NOT_FOUND, "Server Not Found")
                );
    }

    private void validateCategory(Long categoryId) {
        if(categoryId != null)
        {
            categoryRepository.findById(categoryId)
                    .orElseThrow(() ->
                            new CategoryException(Code.NOT_FOUND, "Category Not Found")
                    );
        }
    }
}
