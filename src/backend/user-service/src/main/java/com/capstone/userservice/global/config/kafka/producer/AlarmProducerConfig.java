package com.capstone.userservice.global.config.kafka.producer;

import com.capstone.userservice.global.config.kafka.dto.FriendAlarmEventDto;
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
public class AlarmProducerConfig {
    @Value("{spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> alarmEventProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return config;
    }

    @Bean
    public ProducerFactory<String, FriendAlarmEventDto> friendAlarmEventDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(alarmEventProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, FriendAlarmEventDto> friendAlarmEventDtoKafkaTemplate() {
        return new KafkaTemplate<>(friendAlarmEventDtoProducerFactory());
    }
}
