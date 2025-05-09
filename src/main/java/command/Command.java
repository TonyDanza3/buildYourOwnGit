package command;

import command.commands.Commands;
import filesystem.FileSystem;

public abstract class Command {

    protected String commandName;
    protected FileSystem fileSystem;

    public Command(Commands commandName) {
        this.commandName = commandName.getCommandName();
        fileSystem = new FileSystem();
    }

    public String getCommandName() {
        return commandName;
    }

    protected abstract void execute();

    protected abstract void validateArgs();
}