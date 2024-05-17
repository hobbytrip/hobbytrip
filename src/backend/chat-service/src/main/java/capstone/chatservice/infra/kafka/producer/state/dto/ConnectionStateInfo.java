package capstone.chatservice.infra.kafka.producer.state.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConnectionStateInfo {

    private Long userId;
    private String sessionId;
    private String type;
    private String state;

    @Builder
    public ConnectionStateInfo(Long userId, String sessionId, String type, String state) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.type = type;
        this.state = state;
    }
}
