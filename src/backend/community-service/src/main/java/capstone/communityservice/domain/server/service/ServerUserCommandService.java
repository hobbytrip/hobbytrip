package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.server.dto.ServerUserCreateRequestDto;
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

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerUserCommandService {

    private final ServerUserRepository serverUserRepository;

    public void save(ServerUser serverUser) {
        serverUserRepository.save(serverUser);
    }

    public ServerUserResponseDto update(ServerUserUpdateDto requestDto) {
        ServerUser findServerUser = serverUserRepository.findByServerIdAndUserId(
                requestDto.getServerId(), requestDto.getUserId()
        ).orElseThrow(() -> new ServerException(Code.NOT_FOUND, "Not Found ServerUser"));

        findServerUser.setName(requestDto.getName());

        return ServerUserResponseDto.of(findServerUser);
    }
}
