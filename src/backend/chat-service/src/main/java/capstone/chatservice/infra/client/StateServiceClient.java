package capstone.chatservice.infra.client;

import capstone.chatservice.global.common.dto.DataResponseDto;
import capstone.chatservice.infra.kafka.producer.dto.ConnectionStateInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "state-service")
public interface StateServiceClient {

    @PostMapping("/connection/state")
    DataResponseDto<String> saveConnectionStateInfo(@RequestBody ConnectionStateInfo sessionDto);
}