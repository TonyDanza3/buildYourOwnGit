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

    public static Commands getByCommandName(String commandName) {
        for(Commands command : Commands.values()) {
            if(commandName.equals(command.getCommandName())){
                return command;
            }
        }
        throw new IllegalArgumentException("There is no command with name " + commandName);
    }
}