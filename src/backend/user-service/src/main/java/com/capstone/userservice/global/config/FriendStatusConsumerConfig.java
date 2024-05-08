package com.capstone.userservice.global.config;


import com.capstone.userservice.domain.friend.dto.FriendStatusDto;
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
public class FriendStatusConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.friend-status}")
    private String groupId;

    @Bean
    public Map<String, Object> friendStatusConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, FriendStatusDto> friendStatusConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                friendStatusConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(FriendStatusDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FriendStatusDto> friendStatusContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FriendStatusDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(friendStatusConsumerFactory());
        return factory;
    }
}
