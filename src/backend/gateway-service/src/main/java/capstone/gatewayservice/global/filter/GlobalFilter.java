package capstone.gatewayservice.global.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config){
        //Custom Pre Filter 적용
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("GLOBAL FILTER baseMessage: {}", config.getBaseMessage());

            if(config.isPreLogger())
                log.info("GLOBAL FILTER START: 요청 path -> {}", request.getPath());


            //Custom Post Filter 적용
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //Mono -> Spring Webflux 기능: 동기 방식이 아니라 비동기 방식의 서버를 지원할 때 단일 값 전달 활용 때 사용
                if(config.isPostLogger())
                    log.info("GLOBAL FILTER END: 응답 code -> {}", response.getStatusCode());
            }));


        });
    }

    @Getter
    @Setter
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
