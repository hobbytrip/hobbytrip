package capstone.chatservice.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("capstone.chatservice.infra.client")
class OpenFeignConfig {

}