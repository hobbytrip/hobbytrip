package capstone.chatservice.global.config.kafka.consumer.community;

import capstone.chatservice.infra.kafka.consumer.community.dto.CommunityCategoryEventDto;
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
public class CategoryEventConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.category-event}")
    private String groupId;

    @Bean
    public Map<String, Object> categoryEventConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, CommunityCategoryEventDto> categoryEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                categoryEventConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(CommunityCategoryEventDto.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CommunityCategoryEventDto> categoryEventListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CommunityCategoryEventDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(categoryEventConsumerFactory());
        return factory;
    }
}
