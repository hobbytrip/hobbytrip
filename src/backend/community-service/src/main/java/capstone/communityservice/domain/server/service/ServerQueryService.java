package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.category.dto.CategoryResponseDto;
import capstone.communityservice.domain.category.repository.CategoryRepository;
import capstone.communityservice.domain.channel.dto.ChannelResponseDto;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import capstone.communityservice.domain.server.dto.ServerReadResponseDto;
import capstone.communityservice.domain.server.dto.ServerResponseDto;
import capstone.communityservice.domain.server.entity.ServerUser;
import capstone.communityservice.global.external.ChatServiceFakeClient;
import capstone.communityservice.global.external.dto.ServerMessageDto;
import capstone.communityservice.global.external.dto.ServerUserLocDto;
import capstone.communityservice.global.external.dto.ServerUserStateRequestDto;
import capstone.communityservice.global.external.dto.ServerUserStateResponseDto;
import capstone.communityservice.domain.server.dto.ServerWithCountResponseDto;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.server.repository.ServerUserRepository;
import capstone.communityservice.global.common.dto.PageResponseDto;
import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.external.StateServiceFakeClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServerQueryService {

    // private final StateServiceClient stateServiceClient;
    private final StateServiceFakeClient stateServiceFakeClient;
    // private final ChatServiceClient chatServiceClient;
    private final ChatServiceFakeClient chatServiceFakeClient;

    private final ServerRepository serverRepository;
    private final ServerUserRepository serverUserRepository;
    private final ChannelRepository channelRepository;
    private final CategoryRepository categoryRepository;

    public ServerReadResponseDto read(Long serverId, Long userId) {
        Server findServer = validateExistServer(serverId);

        validateServerUser(serverId, userId);

        List<Long> userIds = serverUserRepository.findUserIdsByServerId(serverId);

        ServerUserStateResponseDto usersOnOff = stateServiceFakeClient.checkServerOnOff(
                ServerUserStateRequestDto.of(serverId, userIds)
        );

        // 유저 최근 채널 위치 불러오는 로직
        // ServerUserLocDto userLocation = stateServiceClient.userLocation(userId);
        ServerUserLocDto userLocation = stateServiceFakeClient.userLocation(userId);

        Page<ServerMessageDto> messages = chatServiceFakeClient.getServerMessages(
                userLocation.getChannelId()
        );

        List<ChannelResponseDto> channels = channelRepository.findByServerId(serverId)
                .stream()
                .map(ChannelResponseDto::of)
                .toList();

        List<CategoryResponseDto> categories = categoryRepository.findByServerId(serverId)
                .stream()
                .map(CategoryResponseDto::of)
                .toList();

        return ServerReadResponseDto.of(
                ServerResponseDto.of(findServer),
                categories,
                channels,
                usersOnOff,
                messages
        );
    }

    public List<ServerResponseDto> search() {
        return serverRepository.findTopOpenServer()
                .stream()
                .map(ServerResponseDto::of)
                .collect(Collectors.toList());
    }

    public PageResponseDto searchCondition(String name, int pageNo) {
        int page = pageNo == 0 ? 0 : pageNo - 1;
        int pageLimit = 10;

        Pageable pageable = PageRequest.of(page, pageLimit);

        Page<Server> servers = serverRepository.findServerWithPaging(name, pageable);

        return PageResponseDto.of(servers, ServerWithCountResponseDto::of);
    }

    public void validateManager(Long managerId, Long userId){
        if(!managerId.equals(userId)){
            throw new ServerException(Code.UNAUTHORIZED, "Not Manager");
        }
    }

    public Server validateExistServer(Long serverId){
        return serverRepository.findById(serverId)
                .orElseThrow(() ->
                        new ServerException(Code.NOT_FOUND, "Server Not Found")
                );
    }

    private void validateServerUser(Long serverId, Long userId) {
        serverUserRepository.findByServerIdAndUserId(serverId, userId)
                .orElseThrow(() -> new ServerException(
                        Code.NOT_FOUND, "Not Found ServerUser")
                );
    }
}
