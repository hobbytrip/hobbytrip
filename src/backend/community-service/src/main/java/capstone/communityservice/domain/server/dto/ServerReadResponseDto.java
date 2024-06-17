package capstone.communityservice.domain.server.dto;

import capstone.communityservice.domain.category.dto.CategoryResponseDto;
import capstone.communityservice.domain.channel.dto.ChannelResponseDto;
import capstone.communityservice.global.external.dto.ServerMessageDto;
import capstone.communityservice.global.external.dto.ServerUsersStateResponse;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerReadResponseDto {
    private ServerResponseDto server;
    private List<ServerUserInfo> serverUserInfos;
    private List<CategoryResponseDto> categories;
    private List<ChannelResponseDto> channels;
    private ServerUsersStateResponse usersState;
    private Page<ServerMessageDto> messages;

    public static ServerReadResponseDto of(
            ServerResponseDto server,
            List<ServerUserInfo> serverUserInfos,
            List<CategoryResponseDto> categories,
            List<ChannelResponseDto> channels,
            ServerUsersStateResponse usersState,
            Page<ServerMessageDto> messages
    ){
        return ServerReadResponseDto.builder()
                .server(server)
                .serverUserInfos(serverUserInfos)
                .categories(categories)
                .channels(channels)
                .usersState(usersState)
                .messages(messages)
                .build();
    }
}
