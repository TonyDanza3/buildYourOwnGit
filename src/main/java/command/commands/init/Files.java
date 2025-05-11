package command.commands.init;

public enum Files {
    HEAD("HEAD"),
    CONFIG("config"),
    DESCRIPTION("description");

    private final String fileName;

    Files(String fileName) {
        this.fileName = fileName;
    }

    public String getFolderName(){
        return this.fileName;
    }
}
