package capstone.gatewayservice.global.filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WhiteListURI {
    USER_LOGIN("/login"),
    USER_SIGNUP("/signup"),
    COMMUNITY_SIGNUP("/user"),
    STOMP("/ws-stomp")
    ;
    final String uri;
}