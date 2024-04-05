package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.server.dto.ServerUserCreateRequestDto;
import capstone.communityservice.domain.server.entity.ServerUser;
import capstone.communityservice.domain.server.repository.ServerUserRepository;
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


    public void save(ServerUserCreateRequestDto requestDto) {
        serverUserRepository.save(ServerUser.of(requestDto));
    }

    public void save(ServerUser serverUser) {
        serverUserRepository.save(serverUser);
    }
}
