package capstone.chatservice.infra.kafka.consumer.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityServerEventDto {

    private String type;

    private Long serverId;

    private String profile;

    private String name;
}
