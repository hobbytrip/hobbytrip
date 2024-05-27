package capstone.communityservice.global.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.topic.community-server-event}")
    private String communityServerEventTopic;

    @Value("${spring.kafka.topic.community-dm-event}")
    private String communityDmEventTopic;

    @Value("${spring.kafka.topic.community-channel-event}")
    private String communityChannelEventTopic;

    @Value("${spring.kafka.topic.community-category-event}")
    private String communityCategoryEventTopic;

    @Value("${spring.kafka.topic.community-forum-event}")
    private String communityForumEventTopic;

    @Value("${spring.kafka.topic.user-location-event}")
    private String userLocationEventTopic;
    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configurations);
    }

    @Bean
    public NewTopic communityServerEventTopic() {
        return TopicBuilder.name(communityServerEventTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic communityDmEventTopic() {
        return TopicBuilder.name(communityDmEventTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic communityChannelEventTopic() {
        return TopicBuilder.name(communityChannelEventTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic communityCategoryEventTopic() {
        return TopicBuilder.name(communityCategoryEventTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic communityForumEventTopic() {
        return TopicBuilder.name(communityForumEventTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userLocationEventTopic() {
        return TopicBuilder.name(userLocationEventTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
