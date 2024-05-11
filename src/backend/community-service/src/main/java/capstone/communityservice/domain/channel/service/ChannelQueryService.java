package capstone.communityservice.domain.channel.service;

import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import capstone.communityservice.domain.channel.exception.ChannelException;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import capstone.communityservice.domain.forum.dto.ForumResponseDto;
import capstone.communityservice.domain.forum.entity.Forum;
import capstone.communityservice.domain.forum.repository.ForumRepository;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.global.common.dto.SliceResponseDto;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChannelQueryService {

    private final ChannelCommandService channelCommandService;

    private final ChannelRepository channelRepository;
    private final ForumRepository forumRepository;

    public SliceResponseDto read(Long channelId, Long userId, int pageNo, String title) {
        if (validateForumChannel(channelId)) {
            channelCommandService.sendUserLocEvent(channelId, userId);

            int page = pageNo == 0 ? 0 : pageNo - 1;
            int pageLimit = 15;

            Pageable pageable = PageRequest.of(page, pageLimit, Sort.Direction.DESC, "createdAt");

            // Forum 댓글 개수 읽어오기 로직 추가해야 함
            Slice<Forum> forums = getForums(title, pageable);

            Slice<ForumResponseDto> forumResponseSlice = getForumResponseDtos(forums, pageable);

            return new SliceResponseDto<>(forumResponseSlice);
        }

        return null;
    }

    private Slice<ForumResponseDto> getForumResponseDtos(Slice<Forum> forums, Pageable pageable) {
        List<ForumResponseDto> forumList = forums.getContent()
                .stream()
                .map(ForumResponseDto::of)
                .collect(Collectors.toList());

        return new SliceImpl<>(
                forumList, pageable, forums.hasNext());
    }

    private Slice<Forum> getForums(String title, Pageable pageable) {
        Slice<Forum> forums;

        if(title == null || title.trim().isEmpty()){
            forums = forumRepository.findForums(pageable);
        } else{
            forums = forumRepository.findForumsByTitle(title, pageable);
        }
        return forums;
    }


    private boolean validateForumChannel(Long channelId) {
        Channel findChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(
                        Code.NOT_FOUND, "Not Found Channel")
                );

        return findChannel.
                getChannelType().
                equals(ChannelType.FORUM);
    }

}
