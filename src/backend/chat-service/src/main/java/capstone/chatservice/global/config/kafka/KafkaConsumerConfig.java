package capstone.chatservice.global.config.kafka;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
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
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.server-chat}")
    private String groupId;

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, ServerMessageDto> serverChatConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(ServerMessageDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ServerMessageDto> serverChatListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ServerMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(serverChatConsumerFactory());
        return factory;
    }
}