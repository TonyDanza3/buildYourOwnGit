package filesystem;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static utils.FileTestUtils.*;

public class FileSystemTest {

    private final FileSystem fileSystem = new FileSystem();
    private static final String fileSystemRootDir = "src/test/resources/fileSystem";

    @AfterAll
    public static void cleanUp() {
        recursivelyRemoveDirectory(Path.of(fileSystemRootDir));
    }

    @Test
    public void createFileInNonexistentDir() {
        Path coreEngineFolder = Path.of(fileSystemRootDir + "/core");
        Path utilsFolder = Path.of(fileSystemRootDir + "/utils");
        Path coreEngineFile = Path.of(fileSystemRootDir + "/core/Engine.java");
        Path utilsFile = Path.of(fileSystemRootDir + "/utils/Utils.java");
        fileSystem.createFile(coreEngineFile);
        fileSystem.createFile(utilsFile);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(directoryExists(coreEngineFolder))
                .withFailMessage("Directory " + coreEngineFolder + " does not exist but it should have been created")
                .isTrue();
        assertions.assertThat(directoryExists(utilsFolder))
                .withFailMessage("Directory " + utilsFolder + " does not exist but it should have been created")
                .isTrue();
        assertions.assertThat(fileExists(coreEngineFile))
                .withFailMessage("File " + coreEngineFile + " does not exist but it should have been created")
                .isTrue();
        assertions.assertThat(fileExists(utilsFile))
                .withFailMessage("File " + utilsFile + " does not exist but it should have been created")
                .isTrue();
        assertions.assertAll();
    }
}