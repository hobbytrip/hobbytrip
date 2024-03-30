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
import capstone.communityservice.domain.user.service.UserCommandService;
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
    private final CategoryCommandService categoryCommandService;
    private final ChannelCommandService channelCommandService;

    @Transactional
    public ServerResponseDto save(ServerCreateRequestDto requestDto, MultipartFile profile) {
        // String profileUrl = fileUploadService.save(profile); <- S3 등록 후
        String profileUrl = "http://image.png";
        String invitationCode = this.createInvitationCode();

        User user = userQueryService.findUserById(requestDto.getManagerId());

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
}
