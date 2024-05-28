package capstone.chatservice.global.config.kafka.consumer.state;

import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateEventDto;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class ConnectionStateEventConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.connection-state-event}")
    private String groupId;

    @Bean
    public Map<String, Object> connectionStateEventConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, ConnectionStateEventDto> connectionStateEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                connectionStateEventConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(ConnectionStateEventDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ConnectionStateEventDto> connectionStateEventListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ConnectionStateEventDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(connectionStateEventConsumerFactory());
        return factory;
    }
}
