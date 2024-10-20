package capstone.chatservice.domain.server.controller;

import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageDeleteRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;
import capstone.chatservice.domain.server.service.command.ServerMessageCommandService;
import capstone.chatservice.infra.S3.FileStore;
import capstone.chatservice.infra.kafka.producer.alarm.AlarmEventProducer;
import capstone.chatservice.infra.kafka.producer.alarm.dto.MentionType;
import capstone.chatservice.infra.kafka.producer.alarm.dto.ServerAlarmEventDto;
import capstone.chatservice.infra.kafka.producer.state.StateEventProducer;
import capstone.chatservice.infra.kafka.producer.state.dto.UserLocationEventDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerMessageCommandController {

    private final FileStore fileStore;
    private final AlarmEventProducer alarmEventProducer;
    private final StateEventProducer stateEventProducer;
    private final ServerMessageCommandService commandService;

    @MessageMapping("/server/message/send")
    public void save(ServerMessageCreateRequest createRequest) {
        commandService.save(createRequest);
        if (!createRequest.getMentionType().equals(MentionType.NO_ALERT)) {
            ServerAlarmEventDto serverAlarmEventDto = ServerAlarmEventDto.from(createRequest);
            alarmEventProducer.sendToServerAlarmEventTopic(serverAlarmEventDto);
        }
    }

    @MessageMapping("/server/message/modify")
    public void modify(ServerMessageModifyRequest modifyRequest) {
        commandService.modify(modifyRequest);
    }

    @MessageMapping("/server/message/delete")
    public void delete(ServerMessageDeleteRequest deleteRequest) {
        commandService.delete(deleteRequest);
    }

    @PostMapping("/server/message/file")
    public void uploadFile(@RequestPart ServerMessageCreateRequest createRequest,
                           @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        List<UploadFile> uploadFiles = fileStore.storeFiles(files);
        createRequest.setFiles(uploadFiles);
        commandService.save(createRequest);
    }

    @PostMapping("/server/user/location")
    public void saveUserLocation(@RequestBody UserLocationEventDto userLocationEventDto) {
        stateEventProducer.sendToUserLocationEventTopic(userLocationEventDto);
    }
}
