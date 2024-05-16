package capstone.chatservice.infra.kafka.consumer.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDmEventDto {

    private String type;

    private Long dmId;

    private String name;

    private String profile;
}
