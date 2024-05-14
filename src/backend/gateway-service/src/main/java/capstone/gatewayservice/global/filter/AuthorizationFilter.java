package capstone.gatewayservice.global.filter;

import capstone.gatewayservice.global.common.JwtTokenProvider;
import capstone.gatewayservice.global.common.dto.ErrorResponseDto;
import capstone.gatewayservice.global.exception.Code;
import capstone.gatewayservice.global.external.AuthClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthClient authClient;

    public AuthorizationFilter(JwtTokenProvider jwtTokenProvider, @Lazy @Qualifier("AuthFeignClient") AuthClient authClient){
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
        this.authClient = authClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String uri = exchange.getRequest().getURI().getPath();

            if(isWhiteList(uri)){
                return chain.filter(exchange);
            }
            
            String accessToken =
                    jwtTokenProvider.resolveAccessToken(exchange.getRequest());

            String refreshToken =
                    jwtTokenProvider.resolveRefreshToken(exchange.getRequest());

            if(!StringUtils.hasText(refreshToken)){
                log.error("API Gateway - RefreshToken validation error");
            } else{
                try {
                    if (StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)
                            && doNotLogout(accessToken)) {
                        return chain.filter(exchange); // Token is valid, continue to the next filter
                    }
                }
                catch (RuntimeException e){
                    log.error("API Gateway - AccessToken validation error: {}", e.getMessage());
                }
            }

            return unauthorizedResponse(exchange); // Token is not valid, respond with unauthorized
        };
    }

    private boolean isWhiteList(String uri) {
        for (WhiteListURI whiteListURI : WhiteListURI.values()) {
            if (whiteListURI.uri.equals(uri)) {
                return true;
            }
        }
        return false;
    }

    private boolean doNotLogout(String accessToken) {
        return authClient.isValidToken(accessToken);
    }

    // 인증 실패 Response
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(Code.UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes;

        try{
            bytes = objectMapper.writeValueAsBytes(errorResponseDto);
        } catch (JsonProcessingException e) {
            bytes = "{}".getBytes(StandardCharsets.UTF_8);
        }

        // 응답 헤더 설정
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        DataBuffer buffer = bufferFactory.wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    // Config할 inner class -> 설정파일에 있는 args
    public static class Config{
    }
}