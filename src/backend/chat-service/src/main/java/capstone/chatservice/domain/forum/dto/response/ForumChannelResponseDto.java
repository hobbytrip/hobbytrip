package capstone.chatservice.domain.forum.dto.response;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumChannelResponseDto {

    private Map<Long, Long> forumsMessageCount;
}
