package internal.index;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
public class IndexRecord {

    private final Mode mode;
    private final String objectSHA;
    private final StagePhase stagePhase;
    private final String fileName;
}