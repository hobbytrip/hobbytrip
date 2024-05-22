package capstone.stateservice.domain.userstate.domain;

import capstone.stateservice.domain.model.ChannelType;
import capstone.stateservice.domain.model.ConnectionState;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "user_state", timeToLive = 30000)
public class UserState {

    @Id
    private Long userId;

    @Indexed
    private String chatSessionId;

    private ConnectionState connectionState;

    private Map<Long, Map<Long, ChannelType>> currentChannel;

    @Builder
    public UserState(Long userId, String chatSessionId,
                     ConnectionState connectionState,
                     Map<Long, Map<Long, ChannelType>> currentChannel) {
        this.userId = userId;
        this.chatSessionId = chatSessionId;
        this.connectionState = connectionState;
        this.currentChannel = currentChannel;
    }

    public void modify(String chatSessionId, ConnectionState connectionState) {
        this.chatSessionId = chatSessionId;
        this.connectionState = connectionState;
    }

    public void modify(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }
}