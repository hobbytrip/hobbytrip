package capstone.chatservice.global.config.kafka.consumer;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
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
public class DirectChatConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.direct-chat}")
    private String groupId;


    @Bean
    public Map<String, Object> directChatConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, DirectMessageDto> directChatConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                directChatConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(DirectMessageDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DirectMessageDto> directChatListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DirectMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(directChatConsumerFactory());
        return factory;
    }
}
