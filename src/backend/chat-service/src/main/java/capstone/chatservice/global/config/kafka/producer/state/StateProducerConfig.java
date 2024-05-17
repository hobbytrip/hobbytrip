package capstone.chatservice.global.config.kafka.producer.state;

import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateEventDto;
import capstone.chatservice.infra.kafka.producer.state.dto.ConnectionStateInfo;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class StateProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> stateEventProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return config;
    }

    @Bean
    public ProducerFactory<String, ConnectionStateEventDto> connectionStateEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(stateEventProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, ConnectionStateEventDto> connectionStateEventKafkaTemplate() {
        return new KafkaTemplate<>(connectionStateEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, ConnectionStateInfo> connectionStateInfoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(stateEventProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, ConnectionStateInfo> connectionStateInfoKafkaTemplate() {
        return new KafkaTemplate<>(connectionStateInfoProducerFactory());
    }
}
