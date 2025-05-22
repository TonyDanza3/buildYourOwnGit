package command.utils;

import command.commands.Commands;
import command.commands.init.GitFiles;
import command.commands.init.GitFolders;
import org.assertj.core.api.SoftAssertions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static command.utils.ErrorTemplates.*;
import static constant.FileTemplates.*;
import static constant.Names.GIT_FOLDER_NAME;
import static filesystem.utils.FileSystemTestUtils.*;

public class Assertion {

    public static void checkCommandResult(Commands command, Path repoRootDir) {
        if (command.equals(Commands.INIT)) {
            checkInitCommandResult(repoRootDir);
        }
    }

    private static void checkInitCommandResult(Path repoRootDir) {
        SoftAssertions assertions = new SoftAssertions();
        Path gitSubfolder = Path.of(repoRootDir + "/" + GIT_FOLDER_NAME);
        Path gitConfigPath = Path.of(gitSubfolder + "/" + GitFiles.CONFIG);
        Path gitHeadPath = Path.of(gitSubfolder + "/" + GitFiles.HEAD);
        Path gitDescriptionPath = Path.of(gitSubfolder + "/" + GitFiles.DESCRIPTION);
        Arrays.stream(GitFiles.values()).forEach(file ->
                assertions.assertThat(checkFileExists(Path.of(gitSubfolder + "/" + file.getFileName())))
                        .withFailMessage(FILE_WAS_NOT_CREATED.formatted(file.getFileName()))
                        .isTrue());
        Arrays.stream(GitFolders.values()).forEach(folder ->
                assertions.assertThat(checkDirectoryExists(Path.of(gitSubfolder + "/" + folder.getFolderName())))
                        .withFailMessage(FOLDER_WAS_NOT_CREATED.formatted(folder.getFolderName()))
                        .isTrue());
        assertions.assertThat(fileContentsIsEqualTo(gitConfigPath, CONFIG_FILE_TEMPLATE))
                .withFailMessage(FILE_CONTENTS_WRONG.formatted(GitFiles.CONFIG.getFileName(), CONFIG_FILE_TEMPLATE, fileToString(gitConfigPath)))
                .isTrue();
        assertions.assertThat(fileContentsIsEqualTo(gitConfigPath, HEAD_FILE_TEMPLATE))
                .withFailMessage(FILE_CONTENTS_WRONG.formatted(GitFiles.HEAD.getFileName(), HEAD_FILE_TEMPLATE, fileToString(gitHeadPath)))
                .isTrue();
        assertions.assertThat(fileContentsIsEqualTo(gitConfigPath, DESCRIPTION_FILE_TEMPLATE))
                .withFailMessage(FILE_CONTENTS_WRONG.formatted(GitFiles.DESCRIPTION.getFileName(), DESCRIPTION_FILE_TEMPLATE, fileToString(gitDescriptionPath)))
                .isTrue();
        assertions.assertAll();
    }
}