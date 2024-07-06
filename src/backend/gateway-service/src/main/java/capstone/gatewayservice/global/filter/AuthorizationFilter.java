package capstone.gatewayservice.global.filter;

import capstone.gatewayservice.global.common.JwtTokenValidator;
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
import org.springframework.http.MediaType;
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

    private final JwtTokenValidator jwtTokenValidator;

    private final WebClient.Builder webClientBuilder;

    public AuthorizationFilter(JwtTokenValidator jwtTokenValidator, @Lazy @Qualifier("AuthFeignClient") AuthClient authClient, WebClient.Builder webClientBuilder){
        super(Config.class);
        this.jwtTokenValidator = jwtTokenValidator;
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
                    jwtTokenValidator.resolveAccessToken(exchange.getRequest());

            String refreshToken =
                    jwtTokenValidator.resolveRefreshToken(exchange.getRequest());


            if (StringUtils.hasText(accessToken) && jwtTokenValidator.validateToken(accessToken)) {
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
        };
    }

    private boolean isWhiteList(String uri) {
        for (WhiteListURI whiteListURI : WhiteListURI.values()) {
            // 해당 URI가 화이트 리스트에 정의된 URI로 시작하는지 확인
            System.out.println(uri);
            if (uri.startsWith(whiteListURI.uri)) {
                return true;
            }
        }
        // 화이트 리스트에 없으면 false 반환
        return false;
    }


    private Mono<Boolean> doNotLogout(String accessToken, ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
//        System.out.println(accessToken);
        // accessToken을 JSON 객체로 변환
        String requestBody = accessToken;
        return webClientBuilder.build().post()
                .uri("lb://USER-SERVICE/isLogin")
//                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .contentType(MediaType.APPLICATION_JSON) // JSON 컨텐츠 타입 명시
                .body(BodyInserters.fromValue(requestBody)) // JSON 객체로 변환된 본문 사용
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