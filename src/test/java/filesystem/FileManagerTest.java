package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.FileTestUtils.*;

public class FileManagerTest {
    private static final String resourcesDir = "src/test/resources";
    private static final String createdFilesFolder = resourcesDir + "/createdFiles";
    private static final Path newTestFile = Paths.get(createdFilesFolder + "/newTestFile");
    private static final Path duplicateFile = Paths.get(createdFilesFolder + "/duplicateFile");
    private static final String nonExistentDirectory = resourcesDir + "/createdFiles/nonExistentDirectory";
    private final FileManager fileManager = new FileManager();

    @AfterAll
    public static void removeDirs() {
        recursivelyRemoveDirectory(Paths.get(createdFilesFolder));
        recursivelyRemoveDirectory(newTestFile);
        recursivelyRemoveDirectory(duplicateFile);
        recursivelyRemoveDirectory(Paths.get(nonExistentDirectory));
    }

    @Test
    public void createNewFile() {
        createDirIfNotExists(createdFilesFolder);
        assertThat(fileExists(newTestFile))
                .as("Test file should not yet be created")
                .isFalse();
        fileManager.createFile(newTestFile);
        assertThat(fileExists(newTestFile))
                .as("Test file is not created")
                .isTrue();
    }

    @Test
    public void createDuplicateFile() {
        createDirIfNotExists(createdFilesFolder);
        fileManager.createFile(duplicateFile);
        assertThrows(RuntimeException.class, () -> fileManager.createFile(duplicateFile));
    }

    @Test
    public void createFileInNonExistentDirectory() {
        Path fileDir = Paths.get(nonExistentDirectory + "/someFile");
        assertThrows(RuntimeException.class, () -> fileManager.createFile(fileDir));
    }

    private boolean fileExists(Path filePath) {
        File file = new File(filePath.toString());
        if (file.isDirectory()) {
            throw new RuntimeException(filePath + " is expected to be a file but it is a directory");
        }
        return file.exists();
    }

    private void createDirIfNotExists(String dir) {
        if (!directoryExists(Paths.get(dir))) {
            new File(dir).mkdir();
        }
    }
}