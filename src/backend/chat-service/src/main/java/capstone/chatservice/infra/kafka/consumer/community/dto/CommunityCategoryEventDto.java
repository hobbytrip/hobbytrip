package capstone.chatservice.infra.kafka.consumer.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCategoryEventDto {

    private String type;

    private Long serverId;

    private Long categoryId;

    private String name;
}
