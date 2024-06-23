package capstone.stateservice.domain.userstate.domain;

import capstone.stateservice.domain.model.ConnectionState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "user:state", timeToLive = 3000000)
public class UserState {

    @Id
    @Indexed
    private Long userId;

    @Indexed
    private String chatSessionId;

    private ConnectionState connectionState;

    @Builder
    public UserState(Long userId, String chatSessionId,
                     ConnectionState connectionState) {
        this.userId = userId;
        this.chatSessionId = chatSessionId;
        this.connectionState = connectionState;
    }

    public void modify(String chatSessionId, ConnectionState connectionState) {
        this.chatSessionId = chatSessionId;
        this.connectionState = connectionState;
    }

    public void modify(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }
}