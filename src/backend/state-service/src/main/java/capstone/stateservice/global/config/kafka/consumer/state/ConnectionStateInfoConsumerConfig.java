package capstone.stateservice.global.config.kafka.consumer.state;

import capstone.stateservice.infra.kafka.consumer.state.dto.ConnectionStateInfo;
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
public class ConnectionStateInfoConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.connection-state-info}")
    private String groupId;

    @Bean
    public Map<String, Object> connectionStateInfoConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, ConnectionStateInfo> connectionStateInfoConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                connectionStateInfoConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(ConnectionStateInfo.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ConnectionStateInfo> connectionStateInfoListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ConnectionStateInfo> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(connectionStateInfoConsumerFactory());
        return factory;
    }
}
