package com.capstone.userservice.global.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.topic.user-status}")
    private String userStatusTopic;

    @Bean
    public ConsumerFactory<String, >

}
