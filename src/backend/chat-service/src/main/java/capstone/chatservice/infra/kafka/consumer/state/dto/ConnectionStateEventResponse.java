package capstone.chatservice.infra.kafka.consumer.state.dto;

import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionState;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateEventDto;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionStateEventResponse {

    private Long userId;
    private ConnectionType type;
    private ConnectionState state;

    public static ConnectionStateEventResponse from(ConnectionStateEventDto connectionStateEventDto) {
        return new ConnectionStateEventResponse(
                connectionStateEventDto.getUserId(),
                connectionStateEventDto.getType(),
                connectionStateEventDto.getState()
        );
    }
}
