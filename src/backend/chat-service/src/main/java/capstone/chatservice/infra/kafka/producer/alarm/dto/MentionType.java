package capstone.chatservice.infra.kafka.producer.alarm.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MentionType {

    EVERYONE("MENTION-EVERYONE"), HERE("MENTION-HERE"), USER("MENTION-USER"), NO_ALERT("NO_ALERT");

    private final String type;
}
