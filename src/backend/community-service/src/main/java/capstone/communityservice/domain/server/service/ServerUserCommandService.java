package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.server.dto.ServerUserDeleteRequestDto;
import capstone.communityservice.domain.server.dto.ServerUserResponseDto;
import capstone.communityservice.domain.server.dto.ServerUserUpdateDto;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.entity.ServerUser;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.server.repository.ServerUserRepository;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerUserCommandService {

    private final ServerUserRepository serverUserRepository;
    private final ServerQueryService serverQueryService;

    public void save(ServerUser serverUser) {
        serverUserRepository.save(serverUser);
    }

    public ServerUserResponseDto update(ServerUserUpdateDto requestDto) {
        ServerUser findServerUser = validateServerUser(requestDto.getServerId(), requestDto.getUserId());

        findServerUser.setName(requestDto.getName());

        return ServerUserResponseDto.of(findServerUser);
    }

    private ServerUser validateServerUser(Long serverId, Long userId) {
        ServerUser findServerUser = serverUserRepository.findByServerIdAndUserId(
                serverId, userId
        ).orElseThrow(() -> new ServerException(Code.NOT_FOUND, "Not Found ServerUser"));
        return findServerUser;
    }

    public void delete(ServerUserDeleteRequestDto requestDto) {
        ServerUser findServerUser = validateServerUser(
                requestDto.getServerId(),
                requestDto.getUserId()
        );

        serverQueryService.validateManager(
                requestDto.getUserId(),
                requestDto.getServerUserId()
        );

        serverUserRepository.delete(findServerUser);
    }
}
