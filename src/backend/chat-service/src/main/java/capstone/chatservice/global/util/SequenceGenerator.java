package capstone.chatservice.global.util;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import capstone.chatservice.domain.model.AutoIncrementSequence;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SequenceGenerator {

    private final MongoOperations mongoOperations;

    public Long generateSequence(String seqName) {
        AutoIncrementSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true), AutoIncrementSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
