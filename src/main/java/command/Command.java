package command;

import command.commands.Commands;
import filesystem.FileSystem;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static constant.ErrorMessage.NOT_A_GIT_REPOSITORY;
import static constant.Names.GIT_FOLDER;

public abstract class Command {

    protected String commandName;
    protected FileSystem fileSystem;
    protected Consumer<String> routeCommandOutput;
    protected Supplier<Path> getCurrentDirectory;

    protected final Path currentDirectory;

    public Command(Commands commandName, Supplier<Path> getCurrentDirectory, Consumer<String> routeCommandOutput) {
        this.commandName = commandName.getCommandName();
        this.routeCommandOutput = routeCommandOutput;
        this.getCurrentDirectory = getCurrentDirectory;
        currentDirectory = getCurrentDirectory.get();
        fileSystem = new FileSystem();
    }

    public Command(Commands commandName) {
        this.commandName = commandName.getCommandName();
        this.routeCommandOutput = System.out::println;
        this.getCurrentDirectory = () -> Path.of(System.getProperty("user.dir"));
        currentDirectory = getCurrentDirectory.get();
        fileSystem = new FileSystem();
    }

    //TODO implement this logic here because it is the same logic for every command
    protected abstract void validateArgs();

    protected abstract void executeCommand();

    public final void execute() {
        if (this.commandName.equals("init")) {
            validateArgs();
            executeCommand();
        } else {
            if (!isGitRepo()) {
                System.out.println(NOT_A_GIT_REPOSITORY);
                return;
            }
            validateArgs();
            executeCommand();
        }
    }

    public final boolean isGitRepo() {
        return fileSystem.hasFileOrDirectory(getCurrentDirectory.get(), GIT_FOLDER);
    }

    public String getCommandName() {
        return commandName;
    }

    public Path getCurrentDirectory() {
        return currentDirectory;
    }
}
