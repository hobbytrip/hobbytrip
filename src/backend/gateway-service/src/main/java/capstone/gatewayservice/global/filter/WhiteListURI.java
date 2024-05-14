package capstone.gatewayservice.global.filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WhiteListURI {
    USER_LOGIN("/user/user/login"),
    USER_SIGNUP("/user/user/signup"),
    ;

    final String uri;
}