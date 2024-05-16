package capstone.chatservice.domain.forum.controller;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.request.ForumMessageCreateRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageDeleteRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageModifyRequest;
import capstone.chatservice.domain.forum.service.command.ForumMessageCommandService;
import capstone.chatservice.domain.model.UploadFile;
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
public class ForumMessageCommandController {

    private final FileStore fileStore;
    private final KafkaProducer kafkaProducer;
    private final ForumMessageCommandService commandService;

    @MessageMapping("/forum/message/send")
    public void save(ForumMessageCreateRequest createRequest) {
        ForumMessageDto messageDto = commandService.save(createRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }

    @MessageMapping("/forum/message/modify")
    public void modify(ForumMessageModifyRequest modifyRequest) {
        ForumMessageDto messageDto = commandService.modify(modifyRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }

    @MessageMapping("/forum/message/delete")
    public void delete(ForumMessageDeleteRequest deleteRequest) {
        ForumMessageDto messageDto = commandService.delete(deleteRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }

    @PostMapping("/api/chat/forum/message/file")
    public void uploadFile(@RequestPart ForumMessageCreateRequest createRequest,
                           @RequestPart(value = "files", required = false) List<MultipartFile> files)
            throws IOException {

        List<UploadFile> uploadFiles = fileStore.storeFiles(files);
        createRequest.setFiles(uploadFiles);
        ForumMessageDto messageDto = commandService.save(createRequest);
        kafkaProducer.sendToForumChatTopic(messageDto);
    }
}
