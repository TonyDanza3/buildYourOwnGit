package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import filesystem.utils.Assertion;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static filesystem.utils.FileSystemTestUtils.*;
import static filesystem.utils.TestData.*;

public class FileManagerTest {

    private final FileManager fileManager = new FileManager();

    @AfterAll
    public static void removeDirs() {
        recursivelyRemoveDirectory(CREATED_FILES_FOLDER);
        recursivelyRemoveDirectory(NEW_TEST_FILE);
        recursivelyRemoveDirectory(DUPLICATE_FILE);
        recursivelyRemoveDirectory(Paths.get(NON_EXISTENT_DIRECTORY));
        recursivelyRemoveDirectory(DELETED_FILES);
    }

    @Test
    public void createNewFile() {
        createDirIfNotExists(CREATED_FILES_FOLDER);
        Assertion.fileNotExists(NEW_TEST_FILE);
        fileManager.createFile(NEW_TEST_FILE);
        Assertion.fileExists(NEW_TEST_FILE);
    }

    @Test
    public void createDUPLICATE_FILE() {
        createDirIfNotExists(CREATED_FILES_FOLDER);
        fileManager.createFile(DUPLICATE_FILE);
        Assertion.fileExists(DUPLICATE_FILE);
        fileManager.createFile(DUPLICATE_FILE);
        Assertion.fileExists(DUPLICATE_FILE);
    }

    @Test
    public void createFileInNON_EXISTENT_DIRECTORY() {
        Path fileDir = Paths.get(NON_EXISTENT_DIRECTORY + "/someFile");
        assertThrows(RuntimeException.class, () -> fileManager.createFile(fileDir));
    }

    @Test
    public void deleteFile() {
        Path fileDir = Path.of(DELETED_FILES + "/fileToDelete");
        createDirIfNotExists(DELETED_FILES);
        createFile(fileDir);
        Assertion.fileExists(fileDir);
        fileManager.deleteFile(fileDir);
        Assertion.directoryExists(DELETED_FILES);
        Assertion.fileNotExists(fileDir);
    }

    @Test
    public void deleteNonExistentFile() {
        Path nonExistentFile = Path.of(RESOURCES_DIR + "/nonExistentFile");
        Assertion.fileNotExists(nonExistentFile);
        fileManager.deleteFile(nonExistentFile);
        Assertion.fileNotExists(nonExistentFile);
    }
}