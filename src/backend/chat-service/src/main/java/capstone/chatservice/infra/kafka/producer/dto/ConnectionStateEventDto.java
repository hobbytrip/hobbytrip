package capstone.chatservice.infra.kafka.producer.dto;

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
    private String type;
    private String state;
    private List<Long> serverIds;
    private List<Long> roomIds;

    @Builder
    public ConnectionStateEventDto(Long userId, String type, String state,
                                   List<Long> serverIds,
                                   List<Long> roomIds) {
        this.userId = userId;
        this.type = type;
        this.state = state;
        this.serverIds = serverIds;
        this.roomIds = roomIds;
    }
}
