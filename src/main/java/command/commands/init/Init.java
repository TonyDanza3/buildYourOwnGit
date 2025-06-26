package command.commands.init;

import command.Command;
import command.commands.Commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static constant.FileTemplates.*;
import static constant.InfoMessage.REPO_INITIALIZED;
import static constant.Names.*;

public class Init extends Command {

    public Init(Supplier<Path> getCurrentDirectory, Consumer<String> routeCommandOutput) {
        super(Commands.INIT,getCurrentDirectory, routeCommandOutput);
    }

    public Init() {
        super(Commands.INIT);
    }

    @Override
    public void executeCommand(String[]args) {
        initializeRepo();
    }

    @Override
    public boolean validateArgs(String[]args) {
        return true;
    }

    private void reinitializeRepo() {}

    private void initializeRepo() {
        fileSystem.createDirectory(Path.of(fileSystem.currentDirectory + "/" + GIT_FOLDER_NAME));
        Arrays.stream(GitFolders.values()).forEach(folder -> fileSystem.createDirectoryInGitSubdirectory(folder.getFolderName()));
        Arrays.stream(GitFiles.values()).forEach(file -> fileSystem.createFileInGitSubdirectory(file.getFileName()));
        fileSystem.putContentToFile(Path.of(fileSystem.currentDirectory + "/" + HEAD_FILE_SUBDIR), HEAD_FILE_TEMPLATE);
        fileSystem.putContentToFile(Path.of(fileSystem.currentDirectory + "/" + CONFIG_FILE_SUBDIR), CONFIG_FILE_TEMPLATE);
        fileSystem.putContentToFile(Path.of(fileSystem.currentDirectory + "/" + DESCRIPTION_FILE_SUBDIR), DESCRIPTION_FILE_TEMPLATE);
        routeCommandOutput.accept(REPO_INITIALIZED.formatted(fileSystem.currentDirectory));
    }
}