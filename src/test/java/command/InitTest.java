package command;

import command.commands.init.Init;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static command.utils.ErrorTemplates.WRONG_INIT_INFO_MESSAGE;
import static constant.InfoMessage.REPO_INITIALIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static filesystem.utils.FileSystemTestUtils.createDirIfNotExists;
import static filesystem.utils.FileSystemTestUtils.recursivelyRemoveDirectory;
import static filesystem.utils.TestData.INIT_COMMAND_DIRECTORY;

public class InitTest {

//    @AfterEach
//    public void cleanInitDir() {
//        recursivelyRemoveDirectory(INIT_COMMAND_DIRECTORY);
//        createDirIfNotExists(INIT_COMMAND_DIRECTORY);
//    }

    @BeforeAll
    public static void createInitDir() {
        createDirIfNotExists(INIT_COMMAND_DIRECTORY);
    }

//    @AfterAll
//    public static void removeInitDir() {
//        recursivelyRemoveDirectory(INIT_COMMAND_DIRECTORY);
//    }

    @Test
    public void initInNonRepoFolder() {
        List<String> outputs = new ArrayList<>();
        Init init = new Init(() -> INIT_COMMAND_DIRECTORY, outputs::add);
        init.execute();
    }

    @Test
    public void checkInfoMessage() {
        List<String> outputs = new ArrayList<>();
        Init init = new Init(() -> INIT_COMMAND_DIRECTORY, outputs::add);
        init.execute();
        assertThat(outputs.get(0).equals(REPO_INITIALIZED.formatted(INIT_COMMAND_DIRECTORY)))
                .withFailMessage(WRONG_INIT_INFO_MESSAGE.formatted(REPO_INITIALIZED.formatted(INIT_COMMAND_DIRECTORY), outputs.get(0)))
                .isTrue();
    }



    //checkErrorMessage ?
}