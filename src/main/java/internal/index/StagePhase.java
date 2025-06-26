package internal.index;

public enum StagePhase {
    NO_CONFLICT(0),
    ANCESTOR(1),
    OURS(2),
    THEIRS(3);
    private final int phase;

    StagePhase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return phase;
    }
}
