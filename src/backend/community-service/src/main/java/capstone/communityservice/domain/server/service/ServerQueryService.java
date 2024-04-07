package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.server.dto.ServerResponseDto;
import capstone.communityservice.domain.server.dto.ServerWithCountResponseDto;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.server.repository.ServerUserRepository;
import capstone.communityservice.global.common.dto.PageResponseDto;
import capstone.communityservice.global.exception.Code;
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

    private final ServerRepository serverRepository;
    private final ServerUserRepository serverUserRepository;

    public Server validateExistServer(Long serverId){
        return serverRepository.findById(serverId)
                .orElseThrow(() ->
                        new ServerException(Code.NOT_FOUND, "Server Not Found")
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
}
