package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import utils.Assertion;
import java.nio.file.Path;

import static utils.FileTestUtils.*;
import static utils.TestData.*;

public class FileSystemTest {

    private final FileSystem fileSystem = new FileSystem();

    @AfterAll
    public static void cleanUp() {
        recursivelyRemoveDirectory(Path.of(FILESYSTEM_ROOT_DIR));
    }

    @Test
    public void createFileInNonexistentDir() {
        Path coreEngineFolder = Path.of(FILESYSTEM_ROOT_DIR + "/core");
        Path utilsFolder = Path.of(FILESYSTEM_ROOT_DIR + "/utils");
        Path coreEngineFile = Path.of(FILESYSTEM_ROOT_DIR + "/core/Engine.java");
        Path utilsFile = Path.of(FILESYSTEM_ROOT_DIR + "/utils/Utils.java");
        fileSystem.createFile(coreEngineFile);
        fileSystem.createFile(utilsFile);
        Assertion.directoryExists(coreEngineFolder, utilsFolder);
        Assertion.fileExists(coreEngineFile, utilsFile);
    }

    @Test
    public void createDuplicateFile() {
        Path duplicateFile = Path.of(FILESYSTEM_ROOT_DIR + "/duplicateFile");
        fileSystem.createFile(duplicateFile);
        Assertion.fileExists(duplicateFile);
        fileSystem.createFile(duplicateFile);
        Assertion.fileExists(duplicateFile);
    }

    @Test
    public void duplicateDoesNotRewriteFileContents() {
        fileSystem.createFile(FILE_WITH_CONTENTS);
        fileSystem.putContentToFile(FILE_WITH_CONTENTS, FILE_CONTENTS);
        Assertion.fileExists(FILE_WITH_CONTENTS);
        Assertion.fileContentsEqualTo(FILE_WITH_CONTENTS, FILE_CONTENTS);
        fileSystem.createFile(FILE_WITH_CONTENTS);
        Assertion.fileExists(FILE_WITH_CONTENTS);
        Assertion.fileContentsEqualTo(FILE_WITH_CONTENTS, FILE_CONTENTS);
    }

    @Test
    public void fillNonExistentFileWithContents() {
        fileSystem.putContentToFile(NONEXISTENT_FILE, FILE_CONTENTS);
        Assertion.fileExists(NONEXISTENT_FILE);
        Assertion.fileContentsEqualTo(NONEXISTENT_FILE, FILE_CONTENTS);
    }
}