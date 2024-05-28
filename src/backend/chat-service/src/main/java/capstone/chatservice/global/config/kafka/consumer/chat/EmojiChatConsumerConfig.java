package capstone.chatservice.global.config.kafka.consumer.chat;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
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
public class EmojiChatConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.emoji-chat}")
    private String groupId;

    @Bean
    public Map<String, Object> emojiChatConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, EmojiDto> emojiChatConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                emojiChatConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(EmojiDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmojiDto> emojiChatListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmojiDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emojiChatConsumerFactory());
        return factory;
    }
}
