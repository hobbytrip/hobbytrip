package capstone.communityservice.domain.server.service;

import capstone.communityservice.domain.category.dto.response.CategoryResponse;
import capstone.communityservice.domain.category.entity.Category;
import capstone.communityservice.domain.category.repository.CategoryRepository;
import capstone.communityservice.domain.category.service.CategoryCommandService;
import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import capstone.communityservice.domain.channel.service.ChannelCommandService;
import capstone.communityservice.domain.server.dto.request.*;
import capstone.communityservice.domain.server.dto.response.ServerInviteCodeResponse;
import capstone.communityservice.domain.server.dto.response.ServerResponse;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.entity.ServerUser;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.server.repository.ServerUserRepository;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.service.UserQueryService;
import capstone.communityservice.global.common.dto.kafka.CommunityServerEventDto;
import capstone.communityservice.global.common.service.FileUploadService;
import capstone.communityservice.global.common.service.RedisService;
import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerCommandService {
    private static final String INVITE_LINK_PREFIX = "serverId=%d";
    private static final String serverKafkaTopic = "communityServerEventTopic";

    private final FileUploadService fileUploadService;
    private final RedisService redisService;
    private final KafkaTemplate<String, CommunityServerEventDto> serverKafkaTemplate;

    private final UserQueryService userQueryService;
    private final ServerUserCommandService serverUserCommandService;
    private final ServerQueryService serverQueryService;
    private final CategoryCommandService categoryCommandService;
    private final ChannelCommandService channelCommandService;

    private final ServerRepository serverRepository;
    private final ServerUserRepository serverUserRepository;
    private final ChannelRepository channelRepository;
    private final CategoryRepository categoryRepository;

    public ServerResponse create(ServerCreateRequest request, MultipartFile file) {
         String profileUrl = file != null ? uploadProfile(file) : null; // <- S3 등록 후
        // String profileUrl = null;
        System.out.println(profileUrl);

        User user = userQueryService.findUserByOriginalId(request.getUserId());

        Server server = serverRepository.save(
                Server.of(
                        request.getName(),
                        profileUrl,
                        user.getId()
                )
        );

        serverUserCommandService.save(
                ServerUser.of(server, user)
        );

        categoryAndChannelInit(server, user.getId());

        /**
         * 서버 Read
         * 유저 상태 정보(온라인/오프라인) 상태관리 서버로부터 받아오는 로직 필요.
         * 첫 접속시 보여줄 채팅 메시지를 가져오기 위한 채팅 서비스 OpenFeign 작업 필요.
         */
        return ServerResponse.of(server);
    }

    public ServerResponse join(ServerJoinRequest request) {
        Server findServer = validateServerUser(request);
        validateServerJoin(findServer, request);

        User findUser = userQueryService.findUserByOriginalId(request.getUserId());

        verifyInvitationCode(findServer.getId(), request);

        serverUserCommandService.save(ServerUser.of(findServer, findUser));

        Channel defaultChannel = findServer.getChannels().get(0);

        channelCommandService.sendUserLocEvent(
                findUser.getId(),
                findServer.getId(),
                defaultChannel.getId()
        );

        return ServerResponse.of(findServer);
    }

    public ServerResponse update(ServerUpdateRequest request, MultipartFile file) {
        Server findServer = serverQueryService.validateExistServer(request.getServerId());

        validateManager(findServer.getManagerId(), request.getUserId());

        String profileUrl = determineProfileUrl(file, findServer, request.getProfile());

        findServer.setServer(
                request.getName(),
                profileUrl,
                request.isOpen(),
                request.getDescription()
        );

        serverKafkaTemplate.send(serverKafkaTopic, CommunityServerEventDto.of("server-update", findServer));

        printKafkaLog("update");

        return ServerResponse.of(findServer);
    }

    public void delete(ServerDeleteRequest request) {
        Server findServer = serverQueryService.validateExistServer(request.getServerId());

        validateManager(findServer.getManagerId(), request.getUserId());

        validateServerProfileDelete(findServer);

        serverKafkaTemplate.send(serverKafkaTopic, CommunityServerEventDto.of("server-delete", findServer));

        serverDeleteBatch(findServer);

        printKafkaLog("delete");
        serverRepository.delete(findServer);
    }

    public ServerResponse deleteProfile(ServerProfileDeleteRequest request) {
        Server findServer = serverQueryService.validateExistServer(
                request.getServerId()
        );

        validateManager(findServer.getManagerId(), request.getUserId());

        if(findServer.getProfile() != null){
            fileUploadService.delete(findServer.getProfile());
            findServer.setProfile(null);
        }

        serverKafkaTemplate.send(serverKafkaTopic, CommunityServerEventDto.of("server-update", findServer));

        printKafkaLog("update");

        return ServerResponse.of(findServer);
    }

    /**
     * Server내 자체 Category Repository 사용할지 고민
     */
    private void categoryAndChannelInit(Server server, Long userId){
        CategoryResponse initChatCategory
                = categoryCommandService.save(Category.of(server, "채팅 채널"));
        CategoryResponse initVoiceCategory
                = categoryCommandService.save(Category.of(server, "음성 채널"));

        Channel newChannel = channelRepository.save(
                Channel.of(
                        server,
                        initChatCategory.getCategoryId(),
                        ChannelType.CHAT,
                        "일반")
        );

        channelRepository.save(
                Channel.of(
                        server,
                        initVoiceCategory.getCategoryId(),
                        ChannelType.VOICE,
                        "일반")
        );

        channelCommandService.sendUserLocEvent(
                userId,
                server.getId(),
                newChannel.getId()
        );
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

    private void validateServerJoin(Server server, ServerJoinRequest request) {
        boolean isClosedWithoutCode = !server.isOpen() && request.getInvitationCode() == null;
        if (isClosedWithoutCode) {
            throw new ServerException(Code.VALIDATION_ERROR, "Not open server. Require invitationCode");
        }
    }

    private void verifyInvitationCode(Long serverId, ServerJoinRequest request) {
        if (request.getInvitationCode() != null) {
            String storedInvitationCode = redisService.getValues(INVITE_LINK_PREFIX.formatted(serverId));
            validateMatchInvitationCode(storedInvitationCode, request.getInvitationCode());
        }
    }

    private Server validateServerUser(ServerJoinRequest request) {
        Optional<Server> findServer = serverUserRepository.validateServerUser(
                request.getServerId(),
                request.getUserId()
        );

        if(findServer.isPresent()){
            throw new ServerException(Code.VALIDATION_ERROR, "Already Exist User");
        } else{
            return serverQueryService.validateExistServer(request.getServerId());
        }
    }

    private void validateMatchInvitationCode(String value, String invitationCode) {
        if(!value.equals(invitationCode)){
            throw new ServerException(Code.VALIDATION_ERROR, "Not Match InvitationCode");
        }
    }

    private void validateExistServer(Long serverId){
        serverRepository.findById(serverId)
                .orElseThrow(() ->
                        new ServerException(Code.NOT_FOUND, "Server Not Found")
                );
    }

    private String determineProfileUrl(MultipartFile file, Server server, String serverProfile) {
        if (file != null) {
            return serverProfile.equals("null") ? uploadProfile(file) : updateProfile(file, serverProfile, server);
        }
        return serverProfile;
    }

    private String uploadProfile(MultipartFile file) {
         return fileUploadService.save(file); // <- S3 등록 후
//        return "http://image.png"; // 예시 URL
    }

    private String updateProfile(MultipartFile file, String serverProfile, Server server) {
        if (validateProfileWithFile(server, serverProfile)) {
             return fileUploadService.update(file, serverProfile); // <- S3 등록 후
//            return "http://image2.png"; // 예시 URL
        }
        return serverProfile;
    }

    private boolean validateProfileWithFile(Server server, String profileUrl) {
        String profile = server.getProfile();

        return profile == null || profile.equals(profileUrl);
    }

    private void validateManager(Long managerId, Long userId){
        if(!managerId.equals(userId)){
            throw new ServerException(Code.UNAUTHORIZED, "Not Manager");
        }
    }
    private void validateServerProfileDelete(Server server) {
        if(server.getProfile() != null && !server.getProfile().equals("null"))
            fileUploadService.delete(server.getProfile());
    }

    private void serverDeleteBatch(Server server) {
        channelRepository.deleteAllByServerId(server.getId());
        categoryRepository.deleteAllByServerid(server.getId());
        serverUserRepository.deleteAllByServerId(server.getId());
    }

    private void printKafkaLog(String type) {
        log.info("Kafka event send about Server {}", type);
    }
}
