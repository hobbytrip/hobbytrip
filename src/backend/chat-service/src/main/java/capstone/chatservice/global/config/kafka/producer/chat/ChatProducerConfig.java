package capstone.chatservice.global.config.kafka.producer.chat;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.infra.kafka.producer.chat.event.ServerChatEvent;
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
public class ChatProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> chatEventProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return config;
    }

    @Bean
    public ProducerFactory<String, ServerChatEvent> serverChatProducerFactory() {
        return new DefaultKafkaProducerFactory<>(chatEventProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, ServerChatEvent> serverChatKafkaTemplate() {
        return new KafkaTemplate<>(serverChatProducerFactory());
    }

    @Bean
    public ProducerFactory<String, DirectMessageDto> directChatProducerFactory() {
        return new DefaultKafkaProducerFactory<>(chatEventProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, DirectMessageDto> directChatKafkaTemplate() {
        return new KafkaTemplate<>(directChatProducerFactory());
    }

    @Bean
    public ProducerFactory<String, EmojiDto> emojiChatProducerFactory() {
        return new DefaultKafkaProducerFactory<>(chatEventProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, EmojiDto> emojiChatKafkaTemplate() {
        return new KafkaTemplate<>(emojiChatProducerFactory());
    }

    @Bean
    public ProducerFactory<String, ForumMessageDto> forumChatProducerFactory() {
        return new DefaultKafkaProducerFactory<>(chatEventProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, ForumMessageDto> forumChatKafkaTemplate() {
        return new KafkaTemplate<>(forumChatProducerFactory());
    }
}