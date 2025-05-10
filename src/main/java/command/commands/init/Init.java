package command.commands.init;

import command.Command;
import command.commands.Commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static constant.InfoMessage.REPO_INITIALIZED;

public class Init extends Command {

    public Init(Supplier<Path> getCurrentDirectory, Consumer<String> routeCommandOutput) {
        super(Commands.INIT,getCurrentDirectory, routeCommandOutput);
    }

    public Init() {
        super(Commands.INIT);
    }

    @Override
    public void executeCommand() {
        initializeRepo();
    }

    @Override
    public void validateArgs() {}

    private void reinitializeRepo() {}

    private void initializeRepo() {
        //TODO : include reinitialize logic with isGitRepo() method
        fileSystem.createDirectory(Path.of(fileSystem.currentDirectory + "/.git"));
        Arrays.stream(Folders.values()).forEach(dir -> fileSystem.createDirectoryInGitSubdirectory(dir.getFolderName()));
        routeCommandOutput.accept(REPO_INITIALIZED.formatted(fileSystem.currentDirectory));
    }
}