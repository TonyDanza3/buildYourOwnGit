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

    public int getPhaseNumber() {
        return phase;
    }

    public static StagePhase getPhaseByNumber(int phaseNumber) {
        for (StagePhase phase : StagePhase.values()) {
            if (phase.getPhaseNumber() == phaseNumber) {
                return phase;
            }
        }
        throw new RuntimeException("There is no stage phase with number " + phaseNumber);
    }
}
