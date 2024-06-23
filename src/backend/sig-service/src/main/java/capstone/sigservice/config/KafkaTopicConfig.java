package capstone.sigservice.config;

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
    private String bootstrapAddress;

    @Value("${spring.kafka.topic.voice-connection-state-event}")
    private String voiceConnectionStateTopic;
    
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configurations);
    }

    @Bean
    public NewTopic serverChatTopic() {
        return TopicBuilder.name(voiceConnectionStateTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }


}
