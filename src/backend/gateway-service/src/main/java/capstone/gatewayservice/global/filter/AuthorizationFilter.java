package capstone.gatewayservice.global.filter;

import capstone.gatewayservice.global.common.JwtTokenProvider;
import capstone.gatewayservice.global.common.dto.DataResponseDto;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthClient authClient;

    private final WebClient.Builder webClientBuilder;

    public AuthorizationFilter(JwtTokenProvider jwtTokenProvider, @Lazy @Qualifier("AuthFeignClient") AuthClient authClient, WebClient.Builder webClientBuilder){
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
        this.authClient = authClient;
        this.webClientBuilder = webClientBuilder;
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

//            System.out.println(accessToken);

            if (!StringUtils.hasText(refreshToken)) {
                log.error("API Gateway - RefreshToken validation error");
                return unauthorizedResponse(exchange);
            } else {
                if (StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
                    return doNotLogout(accessToken, exchange.getRequest())
                            .flatMap(isValid -> {
                                if (Boolean.TRUE.equals(isValid)) {
                                    // Token is valid, continue to the next filter
                                    return chain.filter(exchange);
                                } else {
                                    // Token is not valid, respond with unauthorized
                                    System.out.println(isValid);
                                    return unauthorizedResponse(exchange);
                                }
                            })
                            .onErrorResume(e -> {
                                log.error("API Gateway - AccessToken validation error: {}", e.getMessage());
                                // Handle runtime exceptions by returning unauthorized response
                                return unauthorizedResponse(exchange);
                            });
                } else {
                    // If accessToken is not valid or not present
                    return unauthorizedResponse(exchange);
                }
            }
        };
    }

    private boolean isWhiteList(String uri) {
        for (WhiteListURI whiteListURI : WhiteListURI.values()) {
            if (whiteListURI.uri.equals(uri)) {
                return true;
            }
        }
//        return true;
        return false;
    }

    private Mono<Boolean> doNotLogout(String accessToken, ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();

        return webClientBuilder.build().post()
                .uri("lb://USER-SERVICE/isLogin")
                .headers(httpHeaders -> httpHeaders.addAll(headers)) // 기존 헤더들을 모두 추가
                .body(BodyInserters.fromValue(accessToken))
                .retrieve()
                .bodyToMono(DataResponseDto.class)
                .map(response -> Boolean.TRUE.equals(response.getData()));
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