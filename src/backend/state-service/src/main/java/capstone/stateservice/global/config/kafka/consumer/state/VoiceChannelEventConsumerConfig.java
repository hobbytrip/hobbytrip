package capstone.stateservice.global.config.kafka.consumer.state;

import capstone.stateservice.infra.kafka.consumer.state.dto.VoiceChannelEventDto;
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
public class VoiceChannelEventConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.voice-connection-state-event}")
    private String groupId;

    @Bean
    public Map<String, Object> voiceChannelEventConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return config;
    }

    @Bean
    public ConsumerFactory<String, VoiceChannelEventDto> voiceChannelEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(voiceChannelEventConsumerConfiguration(), new StringDeserializer(),
                new JsonDeserializer<>(VoiceChannelEventDto.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VoiceChannelEventDto> voiceChannelEventListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, VoiceChannelEventDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(voiceChannelEventConsumerFactory());
        return factory;
    }
}
