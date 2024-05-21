package capstone.stateservice.infra.kafka.consumer.state.dto;

import capstone.stateservice.domain.model.ConnectionState;
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
    private ConnectionType type;
    private ConnectionState state;

    @Builder
    public ConnectionStateInfo(Long userId, String sessionId, ConnectionType type, ConnectionState state) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.type = type;
        this.state = state;
    }
}
