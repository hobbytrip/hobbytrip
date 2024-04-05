package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServerQueryService {

    private final ServerRepository serverRepository;


    public Server findServerByInvitationCode(String invitationCode) {
        return serverRepository.findByInvitationCode(invitationCode).orElseThrow(() -> new ServerException(Code.NOT_FOUND, "Server Not Found With InvitationCode"));
    }
}
