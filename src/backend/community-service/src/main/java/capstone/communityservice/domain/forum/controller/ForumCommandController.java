package capstone.communityservice.domain.forum.controller;

import capstone.communityservice.domain.forum.dto.request.ForumCreateRequest;
import capstone.communityservice.domain.forum.dto.request.ForumDeleteRequest;
import capstone.communityservice.domain.forum.dto.request.ForumUpdateRequest;
import capstone.communityservice.domain.forum.dto.response.ForumCreateResponse;
import capstone.communityservice.domain.forum.dto.response.ForumUpdateResponse;
import capstone.communityservice.domain.forum.service.ForumCommandService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumCommandController {

    private final ForumCommandService forumCommandService;

    @PostMapping
    public DataResponseDto<Object> create(
            @Valid @RequestPart(value = "requestDto") ForumCreateRequest request,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ){
        ForumCreateResponse response = forumCommandService.create(request, files);

        return DataResponseDto.of(response);
    }

    @PatchMapping
    public DataResponseDto<Object> update(
            @Valid @RequestPart(value = "requestDto") ForumUpdateRequest request,
            @RequestPart(name = "filesId", required = false) List<Long> filesId,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ){
        ForumUpdateResponse response = forumCommandService.update(request, filesId, files);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@RequestBody ForumDeleteRequest request){
        forumCommandService.delete(request);

        return DataResponseDto.of("Forum delete success!!");
    }

}
