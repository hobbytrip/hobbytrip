package capstone.communityservice.domain.forum.dto.response;

import lombok.*;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumChannelResponseDto {
    private Map<Long, Long> forumsMessageCount;
}
