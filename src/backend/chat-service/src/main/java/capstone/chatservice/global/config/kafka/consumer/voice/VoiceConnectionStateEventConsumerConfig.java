package capstone.chatservice.global.config.kafka.consumer.voice;

import capstone.chatservice.infra.kafka.consumer.voice.dto.VoiceDto;
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
public class VoiceConnectionStateEventConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.voice-connection-state-event}")
    private String groupId;

    @Bean
    public Map<String, Object> voiceConnectionStateEventConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, VoiceDto> voiceConnectionStateEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                voiceConnectionStateEventConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(VoiceDto.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VoiceDto> voiceConnectionStateEventListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, VoiceDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(voiceConnectionStateEventConsumerFactory());
        return factory;
    }
}
