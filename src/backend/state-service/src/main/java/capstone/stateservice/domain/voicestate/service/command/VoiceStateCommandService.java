package capstone.stateservice.domain.voicestate.service.command;

import capstone.stateservice.infra.kafka.consumer.state.dto.VoiceChannelEventDto;

public interface VoiceStateCommandService {

    void saveVoiceState(VoiceChannelEventDto voiceChannelEventDto);
}
