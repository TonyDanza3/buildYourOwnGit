package command.commands.init;

public enum Folders {
    // TODO: not a folder
    HEAD("HEAD"),
    // TODO: not a folder
    CONFIG("config"),
    // TODO: not a folder
    DESCRIPTION("description"),
    HOOKS("hooks"),
    INFO("info"),
    OBJECTS("objects"),
    OBJECTS_INFO("objects/info"),
    OBJECTS_PACK("objects/pack"),
    REFS("refs"),
    REFS_HEADS("refs/heads"),
    REFS_TAGS("refs/tags");

    private final String folderName;

    Folders(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName(){
        return this.folderName;
    }
}
