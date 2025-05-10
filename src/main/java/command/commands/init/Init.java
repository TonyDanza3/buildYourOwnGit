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

    @Override
    public void executeCommand() {
        initializeRepo();
    }

    @Override
    public void validateArgs() {}

    private void reinitializeRepo() {}

    private void initializeRepo() {
        //TODO : include reinitialize logic
        Arrays.stream(Folders.values()).forEach(dir -> fileSystem.createFileInGitSubdirectory(Path.of(currentDirectory + "/" + dir.getFolderName())));
        routeCommandOutput.accept(REPO_INITIALIZED.formatted(getCurrentDirectory.get()));
    }
}