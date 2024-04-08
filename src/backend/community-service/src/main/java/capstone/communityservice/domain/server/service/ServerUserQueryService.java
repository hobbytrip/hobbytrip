package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.server.repository.ServerUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServerUserQueryService {
    private ServerUserRepository serverUserRepository;

}
