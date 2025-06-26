package internal.index;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
public class IndexField {

    private final Mode mode;
    private final String objectSHA;
    private final StagePhase stagePhase;
    private final String fileName;
}