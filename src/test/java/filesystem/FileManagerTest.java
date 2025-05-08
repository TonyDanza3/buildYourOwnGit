package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import utils.Assertion;

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
        Assertion.fileNotExists(newTestFile);
        fileManager.createFile(newTestFile);
        Assertion.fileExists(newTestFile);
    }

    @Test
    public void createDuplicateFile() {
        createDirIfNotExists(createdFilesFolder);
        fileManager.createFile(duplicateFile);
        Assertion.fileExists(duplicateFile);
        fileManager.createFile(duplicateFile);
        Assertion.fileExists(duplicateFile);
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
        Assertion.fileExists(fileDir);
        fileManager.deleteFile(fileDir);
        Assertion.directoryExists(Path.of(deletedFiles));
        Assertion.fileNotExists(fileDir);
    }

    @Test
    public void deleteNonExistentFile() {
        Path nonExistentFile = Path.of(resourcesDir + "/nonExistentFile");
        Assertion.fileNotExists(nonExistentFile);
        fileManager.deleteFile(nonExistentFile);
        Assertion.fileNotExists(nonExistentFile);
    }
}