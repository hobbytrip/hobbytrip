package capstone.communityservice.global.common.kafka;

import capstone.communityservice.global.common.dto.kafka.CommunityCategoryEventDto;
import capstone.communityservice.global.common.dto.kafka.CommunityChannelEventDto;
import capstone.communityservice.global.common.dto.kafka.CommunityDmEventDto;
import capstone.communityservice.global.common.dto.kafka.CommunityServerEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConfig {

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

    private final KafkaTemplate<String, CommunityServerEventDto> communityServerEventKafkaTemplate;
    private final KafkaTemplate<String, CommunityDmEventDto> communityDmEventKafkaTemplate;
    private final KafkaTemplate<String, CommunityChannelEventDto> communityChannelEventKafkaTemplate;
    private final KafkaTemplate<String, CommunityCategoryEventDto> communityCategoryEventKafkaTemplate;

    public void sendToServerEventTopic(CommunityServerEventDto serverEventDto) {
        communityServerEventKafkaTemplate.send(communityServerEventTopic, serverEventDto);
    }

    public void sendToDmEventTopic(CommunityDmEventDto dmEventDto) {
        communityDmEventKafkaTemplate.send(communityDmEventTopic, dmEventDto);
    }

    public void sendToChannelEventTopic(CommunityChannelEventDto channelEventDto) {
        communityChannelEventKafkaTemplate.send(communityChannelEventTopic, channelEventDto);
    }

    public void sendToCategoryEventTopic(CommunityCategoryEventDto categoryEventDto) {
        communityCategoryEventKafkaTemplate.send(communityCategoryEventTopic, categoryEventDto);
    }
}
