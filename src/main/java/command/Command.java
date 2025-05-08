package command;

import command.commands.Commands;

public abstract class Command {

    String commandName;

    public Command(Commands commandName) {
        this.commandName = commandName.getCommandName();
    }

    public String getCommandName() {
        return commandName;
    }

    abstract void execute();
    abstract void validateArgs();
}
