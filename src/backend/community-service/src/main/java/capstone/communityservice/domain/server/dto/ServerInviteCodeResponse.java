package capstone.communityservice.domain.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerInviteCodeResponse {

    @NotBlank
    private String invitationCode;

    public static ServerInviteCodeResponse of(String invitationCode){
        ServerInviteCodeResponse serverInviteCodeResponse = new ServerInviteCodeResponse();
        serverInviteCodeResponse.setInvitationCode(invitationCode);

        return serverInviteCodeResponse;
    }

    //===Setter 메소드===//
    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
