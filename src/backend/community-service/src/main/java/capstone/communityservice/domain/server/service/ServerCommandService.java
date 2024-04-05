package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.category.dto.CategoryResponseDto;
import capstone.communityservice.domain.category.entity.Category;
import capstone.communityservice.domain.category.service.CategoryCommandService;
import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import capstone.communityservice.domain.channel.service.ChannelCommandService;
import capstone.communityservice.domain.server.dto.ServerCreateRequestDto;
import capstone.communityservice.domain.server.dto.ServerInviteCodeResponse;
import capstone.communityservice.domain.server.dto.ServerJoinRequestDto;
import capstone.communityservice.domain.server.dto.ServerResponseDto;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.entity.ServerUser;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.server.repository.ServerUserRepository;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.service.UserQueryService;
import capstone.communityservice.global.common.service.FileUploadService;
import capstone.communityservice.global.common.service.RedisService;
import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServerCommandService {

    private static final String INVITE_LINK_PREFIX = "serverId=%d";

    private final UserQueryService userQueryService;
    private final FileUploadService fileUploadService;
    private final RedisService redisService;

    private final ServerRepository serverRepository;
    private final ServerUserCommandService serverUserCommandService;
    private final ServerQueryService serverQueryService;
    private final CategoryCommandService categoryCommandService;
    private final ChannelCommandService channelCommandService;

    private final ServerUserRepository serverUserRepository;

    @Transactional
    public ServerResponseDto save(ServerCreateRequestDto requestDto, MultipartFile profile) {
        // String profileUrl = fileUploadService.save(profile); <- S3 등록 후
        String profileUrl = "http://image.png";

        User user = userQueryService.findUserByOriginalId(requestDto.getManagerId());

        Server server = serverRepository.save(
                Server.of(
                        requestDto.getName(),
                        profileUrl,
                        user.getId()
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

    private void categoryInit(Server server){
        CategoryResponseDto initChatCategory
                = categoryCommandService.save(Category.of(server, "채팅 채널"));
        CategoryResponseDto initVoiceCategory
                = categoryCommandService.save(Category.of(server, "음성 채널"));

        channelCommandService.save(Channel.of(server, initChatCategory.getId(), ChannelType.CHAT));
        channelCommandService.save(Channel.of(server, initVoiceCategory.getId(), ChannelType.VOICE));
    }

    public ServerInviteCodeResponse generatedServerInviteCode(Long serverId) {
        validateExistServer(serverId);

        String key = INVITE_LINK_PREFIX.formatted(serverId);

        String value = redisService.getValues(key);

        if(value.equals("false")){
            final String randomCode = RandomUtil.generateRandomCode();
            redisService.setValues(key, randomCode, RedisService.toTomorrow());
            return ServerInviteCodeResponse.of(randomCode);
        }

        return ServerInviteCodeResponse.of(value);
    }


    public ServerResponseDto join(ServerJoinRequestDto serverJoinRequestDto) {
        Optional<Server> byServerIdWithUserId = serverUserRepository.findByServerIdWithUserId(
                serverJoinRequestDto.getServerId(),
                serverJoinRequestDto.getUserId()
        );

        if(byServerIdWithUserId.isPresent()){
            throw new ServerException(Code.VALIDATION_ERROR, "Already Exist User");
        }

        Long serverId = serverJoinRequestDto.getServerId();
        Server findServer = serverQueryService.validateExistServer(serverId);
        User findUser = userQueryService.findUserByOriginalId(serverJoinRequestDto.getUserId());

        String value = redisService.getValues(
                INVITE_LINK_PREFIX.formatted(serverId)
        );

        validateMatchInvitationCode(value, serverJoinRequestDto.getInvitationCode());


        serverUserCommandService.save(ServerUser.of(findServer, findUser));

        return ServerResponseDto.of(findServer);
    }

    private void validateMatchInvitationCode(String value, String invitationCode) {
        if(!value.equals(invitationCode)){
            throw new ServerException(Code.VALIDATION_ERROR, "Not Match InvitationCode");
        }
    }

    public void validateExistServer(Long serverId){
        serverRepository.findById(serverId)
                .orElseThrow(() ->
                        new ServerException(Code.NOT_FOUND, "Server Not Found")
                );
    }
}
