package capstone.communityservice.domain.server.dto.response;

import capstone.communityservice.domain.category.dto.response.CategoryResponse;
import capstone.communityservice.domain.channel.dto.response.ChannelResponse;
import capstone.communityservice.global.external.dto.ServerMessageDto;
import capstone.communityservice.global.external.dto.ServerUsersStateResponse;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerReadResponse {
    private ServerResponse server;
    private List<ServerUserInfo> serverUserInfos;
    private List<CategoryResponse> categories;
    private List<ChannelResponse> channels;
    private ServerUsersStateResponse usersState;
    private Page<ServerMessageDto> messages;

    public static ServerReadResponse of(
            ServerResponse server,
            List<ServerUserInfo> serverUserInfos,
            List<CategoryResponse> categories,
            List<ChannelResponse> channels,
            ServerUsersStateResponse usersState,
            Page<ServerMessageDto> messages
    ){
        return ServerReadResponse.builder()
                .server(server)
                .serverUserInfos(serverUserInfos)
                .categories(categories)
                .channels(channels)
                .usersState(usersState)
                .messages(messages)
                .build();
    }
}
