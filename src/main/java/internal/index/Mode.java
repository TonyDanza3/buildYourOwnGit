package internal.index;

public enum Mode {
    USUAL_FILE(100644),
    EXECUTABLE_FILE(100755),
    SYMBOLIC_LINK(120000),
    GIT_SUBMODULE(160000);

    private final int mode;

    Mode(int mode) {
        this.mode = mode;
    }

    public int getNumberOfMOde() {
        return mode;
    }

    public Mode getMode(int modeNumber) {
        for(Mode mode: Mode.values()) {
            if(mode.getNumberOfMOde() == modeNumber){
                return  mode;
            }
        }
        throw new RuntimeException("There is no mode with number " + modeNumber);
    }
}
