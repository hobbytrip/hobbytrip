package com.capstone.notificationservice.global.config.kafka;


import com.capstone.notificationservice.domain.dm.dto.DmNotificationDto;
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

@Configuration
@EnableKafka
public class DmNotificationConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.dm-notification}")
    private String groupId;


    @Bean
    public Map<String, Object> dmNotificationConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, DmNotificationDto> dmNotificationConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                dmNotificationConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(DmNotificationDto.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DmNotificationDto> dmNotificationListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DmNotificationDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(dmNotificationConsumerFactory());
        return factory;
    }
}
