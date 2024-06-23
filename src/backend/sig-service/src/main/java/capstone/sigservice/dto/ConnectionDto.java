package capstone.sigservice.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDto {

    private Long userId;
    private Long serverId;
    private Long channelId;
    public Map<String, Object> toMap(){
        Map<String, Object> map =new HashMap<>();
        map.put("userId",this.userId);
        map.put("serverId",this.serverId);
        map.put("channelId",this.channelId);
        return map;
    }
}