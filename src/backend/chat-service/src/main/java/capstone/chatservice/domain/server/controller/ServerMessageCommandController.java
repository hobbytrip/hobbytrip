package capstone.chatservice.domain.server.controller;

import capstone.chatservice.domain.model.UploadFile;
import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageDeleteRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;
import capstone.chatservice.domain.server.service.command.ServerMessageCommandService;
import capstone.chatservice.infra.S3.FileStore;
import capstone.chatservice.infra.kafka.producer.KafkaProducer;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ServerMessageCommandController {

    private final FileStore fileStore;
    private final KafkaProducer producerService;
    private final ServerMessageCommandService commandService;

    @MessageMapping("/server/message/send")
    public void save(ServerMessageCreateRequest createRequest) {
        ServerMessageDto messageDto = commandService.save(createRequest);
        producerService.sendToServerChatTopic(messageDto);
    }

    @MessageMapping("/server/message/modify")
    public void modify(ServerMessageModifyRequest modifyRequest) {
        ServerMessageDto messageDto = commandService.modify(modifyRequest);
        producerService.sendToServerChatTopic(messageDto);
    }

    @MessageMapping("/server/message/delete")
    public void modify(ServerMessageDeleteRequest deleteRequest) {
        ServerMessageDto messageDto = commandService.delete(deleteRequest);
        producerService.sendToServerChatTopic(messageDto);
    }

    @PostMapping("/server/message/file")
    public void uploadFile(@RequestPart ServerMessageCreateRequest createRequest,
                           @RequestPart(value = "files", required = false) List<MultipartFile> files)
            throws IOException {

        List<UploadFile> uploadFiles = fileStore.storeFiles(files);
        createRequest.setFiles(uploadFiles);
        ServerMessageDto messageDto = commandService.save(createRequest);
        producerService.sendToServerChatTopic(messageDto);
    }
}
