package capstone.gatewayservice.global.filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WhiteListURI {
    USER_LOGIN("/login"),
    USER_SIGNUP("/signup"),
    ;

    final String uri;
}