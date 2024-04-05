package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.category.dto.CategoryResponseDto;
import capstone.communityservice.domain.category.entity.Category;
import capstone.communityservice.domain.category.service.CategoryCommandService;
import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import capstone.communityservice.domain.channel.service.ChannelCommandService;
import capstone.communityservice.domain.server.dto.ServerCreateRequestDto;
import capstone.communityservice.domain.server.dto.ServerResponseDto;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.entity.ServerUser;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.service.UserQueryService;
import capstone.communityservice.global.common.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServerCommandService {

    private final UserQueryService userQueryService;
    private final FileUploadService fileUploadService;
    private final ServerRepository serverRepository;
    private final ServerUserCommandService serverUserCommandService;
    private final ServerQueryService serverQueryService;
    private final CategoryCommandService categoryCommandService;
    private final ChannelCommandService channelCommandService;

    @Transactional
    public ServerResponseDto save(ServerCreateRequestDto requestDto, MultipartFile profile) {
        // String profileUrl = fileUploadService.save(profile); <- S3 등록 후
        String profileUrl = "http://image.png";
        String invitationCode = this.createInvitationCode();

        User user = userQueryService.findUserByOriginalId(requestDto.getManagerId());

        Server server = serverRepository.save(
                Server.of(
                        requestDto.getName(),
                        profileUrl,
                        user.getId(),
                        invitationCode
                )
        );

        serverUserCommandService.save(ServerUser.of(server, user));
        categoryInit(server);

        /**
         * 유저 상태 정보(온라인/오프라인) 상태관리 서버로부터 받아오는 로직 필요.
         * 첫 접속시 보여줄 채팅 메시지를 가져오기 위한 채팅 서비스 OpenFeign 작업 필요.
         */
        return ServerResponseDto.of(server);
    }

    private String createInvitationCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void categoryInit(Server server){
        CategoryResponseDto initChatCategory
                = categoryCommandService.save(Category.of(server, "채팅 채널"));
        CategoryResponseDto initVoiceCategory
                = categoryCommandService.save(Category.of(server, "음성 채널"));

        channelCommandService.save(Channel.of(server, initChatCategory.getId(), ChannelType.CHAT));
        channelCommandService.save(Channel.of(server, initVoiceCategory.getId(), ChannelType.VOICE));
    }

    public ServerResponseDto join(String invitationCode, Long userId) {
        Server findServer = serverQueryService.findServerByInvitationCode(invitationCode);
        User findUser = userQueryService.findUserByOriginalId(userId);
        serverUserCommandService.save(ServerUser.of(findServer, findUser));

        return ServerResponseDto.of(findServer);
    }

}
