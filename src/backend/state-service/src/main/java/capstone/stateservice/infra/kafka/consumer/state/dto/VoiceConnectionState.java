package capstone.stateservice.infra.kafka.consumer.state.dto;

import lombok.Getter;

@Getter
public enum VoiceConnectionState {

    VOICE_JOIN, VOICE_LEAVE
}