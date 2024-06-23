package capstone.chatservice.infra.kafka.consumer.voice.dto;

import lombok.Getter;

@Getter
public enum VoiceConnectionState {

    VOICE_JOIN, VOICE_LEAVE
}