package com.capstone.notificationservice.global.config.kafka;


import com.capstone.notificationservice.domain.dm.dto.DmNotificationDto;
import com.capstone.notificationservice.domain.user.dto.UserNotificationDto;
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
public class FriendAlarmConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.friend-alarm}")
    private String groupId;


    @Bean
    public Map<String, Object> FriendAlarmConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, UserNotificationDto> FriendAlarmConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                FriendAlarmConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(UserNotificationDto.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserNotificationDto> FriendAlarmListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserNotificationDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(FriendAlarmConsumerFactory());
        return factory;
    }
}
