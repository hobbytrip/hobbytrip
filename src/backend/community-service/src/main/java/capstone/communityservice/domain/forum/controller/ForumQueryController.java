package capstone.communityservice.domain.forum.controller;

import capstone.communityservice.domain.forum.dto.response.ForumReadResponse;
import capstone.communityservice.domain.forum.service.ForumQueryService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumQueryController {

    private final ForumQueryService forumQueryService;

    @GetMapping("/{forumId}")
    public DataResponseDto<Object> read(@PathVariable("forumId") Long forumId){
        ForumReadResponse response = forumQueryService.read(forumId);

        return DataResponseDto.of(response);
    }
}
