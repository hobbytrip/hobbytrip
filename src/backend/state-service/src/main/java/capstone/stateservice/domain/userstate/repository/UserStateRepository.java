package capstone.stateservice.domain.userstate.repository;

import capstone.stateservice.domain.userstate.domain.UserState;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserStateRepository extends CrudRepository<UserState, String> {

    Optional<UserState> findByChatSessionId(String chatSessionId);
}
