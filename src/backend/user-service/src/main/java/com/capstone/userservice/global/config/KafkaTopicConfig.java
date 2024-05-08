package com.capstone.userservice.global.config;


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
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String boostrapServers;

    @Value("${spring.kafka.topic.user-status}")
    private String userStatusEventTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        return new KafkaAdmin(configurations);
    }

    @Bean
    public NewTopic userStatusEventTopic() {
        return TopicBuilder.name(userStatusEventTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
