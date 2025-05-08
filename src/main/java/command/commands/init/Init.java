package command.commands.init;

import command.Command;
import command.commands.Commands;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Init extends Command {

    Supplier<Path> currentDirectory;

    public Init(Supplier<Path> currentDirectory) {
        super(Commands.INIT);
        this.currentDirectory = currentDirectory;
    }

    @Override
    public void execute() {}

    @Override
    public void validateArgs() {}

    private boolean hasGitFolder() {
        return fileSystem.hasFileOrDirectory(currentDirectory.get(), ".git");
        //Path.of(System.getProperty("user.dir"));
    }
}
