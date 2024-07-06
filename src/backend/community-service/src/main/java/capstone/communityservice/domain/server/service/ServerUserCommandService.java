package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.server.dto.request.ServerUserDeleteRequest;
import capstone.communityservice.domain.server.dto.response.ServerUserUpdateResponse;
import capstone.communityservice.domain.server.dto.request.ServerUserUpdateRequest;
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

    public ServerUserUpdateResponse update(ServerUserUpdateRequest request) {
        ServerUser findServerUser = validateServerUser(request.getServerId(), request.getUserId());

        findServerUser.setName(request.getName());

        return ServerUserUpdateResponse.of(findServerUser, request.getServerId(), request.getUserId());
    }

    public void delete(ServerUserDeleteRequest request) {
        ServerUser findServerUser = validateServerUser(
                request.getServerId(),
                request.getServerUserId()
        );

        Server findServer = serverQueryService.validateExistServer(request.getServerId());

        serverQueryService.validateManager(
                findServer.getManagerId(),
                request.getUserId()
        );

        serverUserRepository.delete(findServerUser);
    }

    private ServerUser validateServerUser(Long serverId, Long userId) {
        return serverUserRepository.findByServerIdAndUserId(serverId, userId)
                .orElseThrow(() -> new ServerException(
                        Code.NOT_FOUND, "Not Found ServerUser")
                );
    }
}
