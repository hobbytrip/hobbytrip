package capstone.chatservice.global.config.kafka;

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

    @Value("${spring.kafka.topic.server-chat}")
    private String serverChatTopic;

    @Value("${spring.kafka.topic.direct-chat}")
    private String directChatTopic;

    @Value("${spring.kafka.topic.forum-chat}")
    private String forumChatTopic;

    @Value("${spring.kafka.topic.emoji-chat}")
    private String emojiChatTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configurations);
    }

    @Bean
    public NewTopic serverChatTopic() {
        return TopicBuilder.name(serverChatTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic directChatTopic() {
        return TopicBuilder.name(directChatTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic forumChatTopic() {
        return TopicBuilder.name(forumChatTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic emojiChatTopic() {
        return TopicBuilder.name(emojiChatTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
