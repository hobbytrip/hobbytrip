package capstone.chatservice.infra.kafka.producer.state.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConnectionStateEventDto {

    private Long userId;
    private ConnectionType type;
    private ConnectionState state;
    private List<Long> serverIds;
    private List<Long> roomIds;

    @Builder
    public ConnectionStateEventDto(Long userId, ConnectionType type,
                                   ConnectionState state,
                                   List<Long> serverIds,
                                   List<Long> roomIds) {
        this.userId = userId;
        this.type = type;
        this.state = state;
        this.serverIds = serverIds;
        this.roomIds = roomIds;
    }
}
