package capstone.communityservice.domain.channel.service;

import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.channel.entity.ChannelType;
import capstone.communityservice.domain.channel.exception.ChannelException;
import capstone.communityservice.domain.channel.repository.ChannelRepository;
import capstone.communityservice.domain.forum.dto.response.ForumChannelResponseDto;
import capstone.communityservice.domain.forum.dto.response.ForumResponse;
import capstone.communityservice.domain.forum.entity.Forum;
import capstone.communityservice.domain.forum.repository.ForumRepository;
import capstone.communityservice.global.common.dto.SliceResponseDto;
import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.external.ChatServiceClient;
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

    private final ChatServiceClient chatServiceClient;

    public SliceResponseDto read(Long channelId, Long userId, int pageNo, String title) {
        Channel findChannel = validateExsitsChannel(channelId);

        if (validateForumChannel(findChannel)) {

            channelCommandService.sendUserLocEvent(
                    userId,
                    findChannel.getServer().getId(),
                    channelId
            );

            int page = pageNo == 0 ? 0 : pageNo - 1;
            int pageLimit = 15;

            Pageable pageable = PageRequest.of(page, pageLimit, Sort.Direction.DESC, "createdAt");


            Slice<Forum> forums = getForums(title, channelId, pageable);

            List<Long> forumIds = getForumIds(forums);

            // Forum 댓글 개수 읽어오기 로직
            ForumChannelResponseDto forumsMessageCount = chatServiceClient.getForumsMessageCount(forumIds);

            Slice<ForumResponse> forumResponseSlice = getForumResponseDtos(forums, pageable, forumsMessageCount);

            return new SliceResponseDto<>(forumResponseSlice);
        }

        return null;
    }

    private List<Long> getForumIds(Slice<Forum> forums) {
        return forums.getContent().stream()
                .map(Forum::getId)
                .toList();
    }


    private Slice<ForumResponse> getForumResponseDtos(Slice<Forum> forums, Pageable pageable, ForumChannelResponseDto forumsMessageCount) {
        List<ForumResponse> forumList = forums.getContent()
                .stream()
                .map(forum ->
                        ForumResponse.of(forum,
                                forumsMessageCount.getForumsMessageCount()
                                        .get(
                                                forum.getId()
                                        )
                        )
                )
                .collect(Collectors.toList());

        return new SliceImpl<>(
                forumList, pageable, forums.hasNext());
    }

    private Slice<Forum> getForums(String title, Long channelId, Pageable pageable) {
        Slice<Forum> forums;

        if(title == null || title.trim().isEmpty()){
            forums = forumRepository.findForumsWithChannelId(channelId, pageable);
        } else{
            forums = forumRepository.findForumsByTitleWithChannelId(title, channelId, pageable);
        }
        return forums;
    }


    private boolean validateForumChannel(Channel channel) {
        return channel.
                getChannelType().
                equals(ChannelType.FORUM);
    }

    private Channel validateExsitsChannel(Long channelId){
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(
                        Code.NOT_FOUND, "Not Found Channel"
                ));
    }


}
