package internal.index;

import lombok.Builder;

@Builder
public class IndexRecord {

    public IndexRecord(IndexRecordBuilder builder) {
        this.mode = builder.mode;
        this.objectSHA = builder.objectSHA;
        this.stagePhase = builder.stagePhase;
        this.fileName = builder.fileName;
    }

    private final Mode mode;
    private final String objectSHA;
    private final StagePhase stagePhase;
    private final String fileName;

    public Mode getMode() {
        return mode;
    }

    public String getObjectSHA() {
        return objectSHA;
    }

    public StagePhase getStagePhase() {
        return stagePhase;
    }

    public String getFileName() {
        return fileName;
    }

    public static IndexRecordBuilder builder() {
        return new IndexRecordBuilder();
    }

    public static class IndexRecordBuilder {
        private Mode mode;
        private String objectSHA;
        private StagePhase stagePhase;
        private String fileName;

        public IndexRecordBuilder mode(Mode mode) {
            this.mode = mode;
            return this;
        }

        public IndexRecordBuilder objectSHA(String objectSHA) {
            this.objectSHA = objectSHA;
            return this;
        }

        public IndexRecordBuilder stagePhase(StagePhase stagePhase) {
            this.stagePhase = stagePhase;
            return this;
        }

        public IndexRecordBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public IndexRecord build() {
            return new IndexRecord(this);
        }

    }
}