package command;

import command.commands.Commands;
import filesystem.FileSystem;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static constant.ErrorMessage.NOT_A_GIT_REPOSITORY;
import static constant.Names.GIT_FOLDER_NAME;

public abstract class Command {

    protected String commandName;
    protected FileSystem fileSystem;
    protected Consumer<String> routeCommandOutput;

    public Command(Commands commandName, Supplier<Path> defineCurrentDirectory, Consumer<String> routeCommandOutput) {
        this.commandName = commandName.getCommandName();
        this.routeCommandOutput = routeCommandOutput;
        fileSystem = new FileSystem(defineCurrentDirectory);
    }

    public Command(Commands commandName) {
        this.commandName = commandName.getCommandName();
        this.routeCommandOutput = System.out::println;
        fileSystem = new FileSystem();
    }

    //TODO implement this logic here because it is the same logic for every command
    protected abstract boolean validateArgs(String[]args);

    protected abstract void executeCommand(String[]args);

    public final void execute(String ... args) {
        if (this.commandName.equals("init")) {
            if (validateArgs(args)) {
                executeCommand(args);
            }
        } else {
            if (!isGitRepo()) {
                System.out.println(NOT_A_GIT_REPOSITORY);
                return;
            }
            if (validateArgs(args)) {
                executeCommand(args);
            }
        }
    }

    //TODO: move to fileSystem class or another class
    public final boolean isGitRepo() {
        return fileSystem.hasFileOrDirectory(fileSystem.currentDirectory, GIT_FOLDER_NAME);
    }

    public String getCommandName() {
        return commandName;
    }
}
