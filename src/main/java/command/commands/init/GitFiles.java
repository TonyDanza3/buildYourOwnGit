package command.commands.init;

public enum GitFiles {
    HEAD("HEAD"),
    CONFIG("config"),
    DESCRIPTION("description");

    private final String fileName;

    GitFiles(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName(){
        return this.fileName;
    }
}
