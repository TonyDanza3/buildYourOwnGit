package command.commands;

public enum Commands {
    INIT("init"),
    COMMIT("commit"),
    ADD("add"),
    STATUS("status"),
    PUSH("push");

    private final String commandName;

    Commands(String command) {
        this.commandName = command;
    }

    public String getCommandName() {
        return commandName;
    }
}