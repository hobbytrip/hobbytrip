package capstone.chatservice.infra.client;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserServerDmInfo {

    private List<Long> roomIds;
    private List<Long> serverIds;
}
