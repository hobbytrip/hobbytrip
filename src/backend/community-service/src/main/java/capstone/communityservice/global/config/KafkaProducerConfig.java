package capstone.communityservice.global.config;

import capstone.communityservice.global.common.dto.kafka.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return config;
    }

    @Bean
    public ProducerFactory<String, CommunityServerEventDto> communityServerEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CommunityServerEventDto> communityServerEventKafkaTemplate() {
        return new KafkaTemplate<>(communityServerEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, CommunityDmEventDto> communityDmEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CommunityDmEventDto> communityDmEventKafkaTemplate() {
        return new KafkaTemplate<>(communityDmEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, CommunityChannelEventDto> communityChannelEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CommunityChannelEventDto> communityChannelEventKafkaTemplate() {
        return new KafkaTemplate<>(communityChannelEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, CommunityCategoryEventDto> communityCategoryEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CommunityCategoryEventDto> communityCategoryEventKafkaTemplate() {
        return new KafkaTemplate<>(communityCategoryEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, CommunityForumEventDto> communityForumEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CommunityForumEventDto> communityForumEventKafkaTemplate() {
        return new KafkaTemplate<>(communityForumEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, UserLocationEventDto> userLocationEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, UserLocationEventDto> userLocationEventKafkaTemplate() {
        return new KafkaTemplate<>(userLocationEventProducerFactory());
    }
}