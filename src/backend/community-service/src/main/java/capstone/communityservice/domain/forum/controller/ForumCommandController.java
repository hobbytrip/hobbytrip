package capstone.communityservice.domain.forum.controller;

import capstone.communityservice.domain.forum.dto.*;
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
            @Valid @RequestPart(value = "requestDto") ForumCreateRequestDto requestDto,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ){
        ForumCreateResponseDto response = forumCommandService.create(requestDto, files);

        return DataResponseDto.of(response);
    }

    @PatchMapping
    public DataResponseDto<Object> update(
            @Valid @RequestPart(value = "requestDto") ForumUpdateRequestDto requestDto,
            @RequestPart(name = "filesId", required = false) List<Long> filesId,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ){
        ForumUpdateResponseDto response = forumCommandService.update(requestDto, filesId, files);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@RequestBody ForumDeleteRequestDto requestDto){
        forumCommandService.delete(requestDto);

        return DataResponseDto.of("Forum delete success!!");
    }

}
