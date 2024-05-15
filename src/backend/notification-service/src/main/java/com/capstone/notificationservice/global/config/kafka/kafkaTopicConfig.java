package com.capstone.notificationservice.global.config.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class kafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.topic.direct-chat}")
    private String dmNotificationTopic;

    @Value("${spring.kafka.topic.server-chat}")
    private String serverNotificationTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configurations);
    }

    @Bean
    public NewTopic dmNotificationTopic() {
        return TopicBuilder.name(dmNotificationTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic serverNotificationTopic() {
        return TopicBuilder.name(serverNotificationTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
