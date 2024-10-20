package capstone.chatservice.domain.dm.controller;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.dto.request.DirectMessageCreateRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageDeleteRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageModifyRequest;
import capstone.chatservice.domain.dm.service.command.DirectMessageCommandService;
import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.infra.S3.FileStore;
import capstone.chatservice.infra.kafka.producer.alarm.AlarmEventProducer;
import capstone.chatservice.infra.kafka.producer.alarm.dto.DmAlarmEventDto;
import capstone.chatservice.infra.kafka.producer.chat.ChatEventProducer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DirectMessageCommandController {

    private final FileStore fileStore;
    private final AlarmEventProducer alarmEventProducer;
    private final ChatEventProducer chatEventProducer;
    private final DirectMessageCommandService commandService;

    @MessageMapping("/direct/message/send")
    public void save(DirectMessageCreateRequest createRequest) {

        DirectMessageDto messageDto = commandService.save(createRequest);
        chatEventProducer.sendToDirectChatTopic(messageDto);
        DmAlarmEventDto dmAlarmEventDto = DmAlarmEventDto.from(createRequest);
        alarmEventProducer.sendToDmAlarmEventTopic(dmAlarmEventDto);
    }

    @MessageMapping("/direct/message/modify")
    public void modify(DirectMessageModifyRequest modifyRequest) {

        DirectMessageDto messageDto = commandService.modify(modifyRequest);
        chatEventProducer.sendToDirectChatTopic(messageDto);
    }

    @MessageMapping("/direct/message/delete")
    public void delete(DirectMessageDeleteRequest deleteRequest) {

        DirectMessageDto messageDto = commandService.delete(deleteRequest);
        chatEventProducer.sendToDirectChatTopic(messageDto);
    }

    @PostMapping("/direct/message/file")
    public void uploadFile(@RequestPart DirectMessageCreateRequest createRequest,
                           @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        List<UploadFile> uploadFiles = fileStore.storeFiles(files);
        createRequest.setFiles(uploadFiles);
        DirectMessageDto messageDto = commandService.save(createRequest);
        chatEventProducer.sendToDirectChatTopic(messageDto);
    }
}
