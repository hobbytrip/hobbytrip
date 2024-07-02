package capstone.communityservice.domain.channel.service;

import capstone.communityservice.domain.category.exception.CategoryException;
import capstone.communityservice.domain.category.repository.CategoryRepository;
import capstone.communityservice.domain.channel.dto.request.ChannelCreateRequest;
import capstone.communityservice.domain.channel.dto.request.ChannelDeleteRequest;
import capstone.communityservice.domain.channel.dto.response.ChannelResponse;
import capstone.communityservice.domain.channel.dto.request.ChannelUpdateRequest;
import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import capstone.communityservice.domain.channel.exception.ChannelException;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import capstone.communityservice.domain.forum.entity.File;
import capstone.communityservice.domain.forum.entity.Forum;
import capstone.communityservice.domain.forum.repository.FileRepository;
import capstone.communityservice.domain.forum.repository.ForumRepository;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.server.service.ServerQueryService;
import capstone.communityservice.global.common.dto.kafka.CommunityChannelEventDto;
import capstone.communityservice.global.common.dto.kafka.UserLocationEventDto;
import capstone.communityservice.global.common.service.FileUploadService;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChannelCommandService {
    private static final String channelKafkaTopic = "communityChannelEventTopic";
    private static final String userLocationKafkaTopic = "userLocationEvent";

    private final KafkaTemplate<String, CommunityChannelEventDto> channelKafkaTemplate;
    private final KafkaTemplate<String, UserLocationEventDto> userLocationKafkaTemplate;

    private final ServerQueryService serverQueryService;
    private final FileUploadService fileUploadService;

    private final ChannelRepository channelRepository;
    private final ServerRepository serverRepository;
    private final CategoryRepository categoryRepository;
    private final ForumRepository forumRepository;
    private final FileRepository fileRepository;

    public ChannelResponse create(ChannelCreateRequest request) {
        Server findServer = validateManagerInChannel(request.getServerId(), request.getUserId());

        validateCategory(request.getCategoryId());

        Channel newChannel = channelRepository.save(createChannel(findServer, request));

        channelKafkaTemplate.send(
                channelKafkaTopic,
                CommunityChannelEventDto.of(
                        "channel-create",
                        newChannel,
                        findServer.getId()
                )
        );

        return ChannelResponse.of(newChannel);
    }

    public ChannelResponse update(ChannelUpdateRequest request) {
        validateManagerInChannel(request.getServerId(), request.getUserId());
        validateCategory(request.getCategoryId());

        Channel findChannel = validateChannel(request.getChannelId());

        findChannel.modifyChannel(request.getCategoryId(), request.getName());

        channelKafkaTemplate.send(channelKafkaTopic, CommunityChannelEventDto.of("channel-update", findChannel, request.getServerId()));

        printKafkaLog("update");

        return ChannelResponse.of(findChannel);
    }

    public void delete(ChannelDeleteRequest request) {
        validateManagerInChannel(request.getServerId(), request.getUserId());

        Channel findChannel = validateChannel(request.getChannelId());

        validateForumChannelDelete(findChannel);
        channelKafkaTemplate.send(userLocationKafkaTopic,
                CommunityChannelEventDto.of(
                        "channel-delete",
                        findChannel,
                        request.getServerId()
                )
        );
        printKafkaLog("delete");

        channelRepository.delete(findChannel);
    }

    private void validateForumChannelDelete(Channel channel) {
        if(channel
                .getChannelType()
                .equals(ChannelType.FORUM)
        ){
            List<Forum> forums = forumRepository.findForumsByChannelId(channel.getId());
            List<Long> forumIds = forums.stream()
                    .map(Forum::getId)
                    .distinct()
                    .toList();

            List<File> files = fileRepository.findByForumIdsIn(forumIds);

            fileRepository.deleteAllByForumIdsIn(forumIds);
            fileDelete(files);


            forumRepository.deleteAllByChannelId(channel.getId());
        }
    }

    private void fileDelete(List<File> files) {
        files.stream()
                .map(File::getFileUrl)
                .forEach(fileUploadService::delete);
    }


    private Channel createChannel(Server findServer, ChannelCreateRequest request){
        return Channel.of(
                findServer,
                request.getCategoryId(),
                request.getChannelType(),
                request.getName()
        );
    }

    public void sendUserLocEvent(Long userId, Long serverId, Long channelId) {
        userLocationKafkaTemplate.send(userLocationKafkaTopic,
                UserLocationEventDto.of(
                        userId,
                        serverId,
                        channelId)
        );

        printKafkaLog("User Location Send");
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

    private void printKafkaLog(String type) {
        log.info("Kafka event send about Channel {}", type);
    }
}
