package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

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
    private static final String deletedFiles = resourcesDir + "/deletedFiles";
    private final FileManager fileManager = new FileManager();

    @AfterAll
    public static void removeDirs() {
        recursivelyRemoveDirectory(Paths.get(createdFilesFolder));
        recursivelyRemoveDirectory(newTestFile);
        recursivelyRemoveDirectory(duplicateFile);
        recursivelyRemoveDirectory(Paths.get(nonExistentDirectory));
        recursivelyRemoveDirectory(Path.of(deletedFiles));
    }

    @Test
    public void createNewFile() {
        createDirIfNotExists(createdFilesFolder);
        assertThat(checkFileExists(newTestFile))
                .as("Test file should not yet be created")
                .isFalse();
        fileManager.createFile(newTestFile);
        assertThat(checkFileExists(newTestFile))
                .as("Test file is not created")
                .isTrue();
    }

    @Test
    public void createDuplicateFile() {
        createDirIfNotExists(createdFilesFolder);
        fileManager.createFile(duplicateFile);
        assertThat(checkFileExists(duplicateFile))
                .withFailMessage("File " + duplicateFile + " does not exists but it should have been created")
                .isTrue();
        fileManager.createFile(duplicateFile);
        assertThat(checkFileExists(duplicateFile))
                .withFailMessage("File " + duplicateFile + " does not exists but it should have been created")
                .isTrue();
    }

    @Test
    public void createFileInNonExistentDirectory() {
        Path fileDir = Paths.get(nonExistentDirectory + "/someFile");
        assertThrows(RuntimeException.class, () -> fileManager.createFile(fileDir));
    }

    @Test
    public void deleteFile() {
        Path fileDir = Path.of(deletedFiles + "/fileToDelete");
        createDirIfNotExists(deletedFiles);
        createFile(fileDir);
        assertThat(checkFileExists(fileDir))
                .withFailMessage("File " + fileDir + " does not exists but it should have been created")
                .isTrue();
        fileManager.deleteFile(fileDir);

        assertThat(checkDirectoryExists(Path.of(deletedFiles)))
                .withFailMessage("Seems like directory " + deletedFiles + " is deleted but it should not have been deleted")
                .isTrue();
        assertThat(checkFileExists(fileDir))
                .withFailMessage("File " + fileDir + " still exists but it should have been deleted")
                .isFalse();
    }

    @Test
    public void deleteNonExistentFile() {
        fileManager.deleteFile(Path.of(resourcesDir + "/nonExistentFile"));
    }
}