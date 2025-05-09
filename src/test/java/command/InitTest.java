package command;

import command.commands.init.Init;
import org.junit.jupiter.api.BeforeAll;

import java.nio.file.Path;

import static utils.FileSystemTestUtils.createDirIfNotExists;
import static utils.TestData.INIT_COMMAND_DIRECTORY;

public class InitTest {

    Init init = new Init(() -> Path.of(INIT_COMMAND_DIRECTORY));

    @BeforeAll
    public static void createDir() {
        createDirIfNotExists(INIT_COMMAND_DIRECTORY);
    }
}
