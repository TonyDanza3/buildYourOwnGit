package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.FileTestUtils.*;

public class DirectoryManagerTest {

    private static final String resourcesDir = "src/test/resources";
    private static final Path createdDir = Path.of(resourcesDir + "/directoryManager");
    private static final Path removedDir = Path.of(resourcesDir + "/directoryToRemove");
    private static final Path recDir = Path.of(resourcesDir + "/directoryRec");
    private static final Path duplicateDir = Path.of(resourcesDir + "/duplicate");
    private static final Path idempotentDuplicateDir = Path.of(resourcesDir + "/idempotentDuplicate");
    private final String ERRshouldHaveBeenCreated = "Directory %s does not exist but it should have been created";
    private final String ERRshouldHaveBeenRemoved = "Directory %s still exists but should have been removed";
    private List<Path> childRecDirs = List.of(
            Path.of(recDir + "/one"),
            Path.of(recDir + "/two"),
            Path.of(recDir + "/three"),
            Path.of(recDir + "/one/one"),
            Path.of(recDir + "/one/two"),
            Path.of(recDir + "/one/three")
    );
    DirectoryManager directoryManager = new DirectoryManager();

    @AfterAll
    public static void clearTestDirectories() {
        recursivelyRemoveDirectory(createdDir);
        recursivelyRemoveDirectory(removedDir);
        recursivelyRemoveDirectory(recDir);
        recursivelyRemoveDirectory(duplicateDir);
        recursivelyRemoveDirectory(idempotentDuplicateDir);
    }

    @Test
    public void createDirectory() {
        assertThat(directoryExists(createdDir))
                .withFailMessage("Directory " + createdDir + " exists but it should not")
                .isFalse();
        directoryManager.createDirectory(createdDir);
        assertThat(directoryExists(createdDir))
                .withFailMessage(formatErrMessage(ERRshouldHaveBeenCreated, createdDir))
                .isTrue();
    }

    @Test
    public void removeDirectory() {
        directoryManager.createDirectory(removedDir);
        assertThat(directoryExists(removedDir))
                .withFailMessage(formatErrMessage(ERRshouldHaveBeenCreated, removedDir))
                .isTrue();
        directoryManager.removeDirectory(removedDir);
        assertThat(directoryExists(removedDir))
                .withFailMessage(formatErrMessage(ERRshouldHaveBeenRemoved, removedDir))
                .isFalse();
    }

    @Test
    public void createdDuplicateDirectory() {
        directoryManager.createDirectory(duplicateDir);
        assertThrows(RuntimeException.class, () -> directoryManager.createDirectory(duplicateDir));
    }

    @Test
    public void idempotentCreateDuplicateDirectory() {
        directoryManager.createDirectoryIfNotExists(idempotentDuplicateDir);
        assertThat(directoryExists(idempotentDuplicateDir))
                .withFailMessage("Directory " + idempotentDuplicateDir + " does not exist but it should have been created")
                .isTrue();
        directoryManager.createDirectoryIfNotExists(idempotentDuplicateDir);
        assertThat(directoryExists(idempotentDuplicateDir))
                .withFailMessage("Directory " + idempotentDuplicateDir + " does not exist but it should have been created")
                .isTrue();
    }

    @Test
    public void removeDirectoryRecursively() {
        directoryManager.createDirectory(recDir);
        childRecDirs.forEach(dir -> directoryManager.createDirectory(dir));
        assertThat(directoryExists(recDir))
                .withFailMessage(formatErrMessage(ERRshouldHaveBeenCreated, removedDir))
                .isTrue();
        assertThat(allExists(childRecDirs)).isTrue();
        directoryManager.removeDirectory(recDir);
        assertThat(directoryExists(recDir))
                .withFailMessage(formatErrMessage(ERRshouldHaveBeenRemoved, recDir))
                .isFalse();
        assertThat(allExists(childRecDirs)).isFalse();
    }

    private String formatErrMessage(String message, Path dirName) {
        Formatter formatter = new Formatter();
        String result = formatter.format(message, dirName).toString();
        formatter.close();
        return result;
    }

    private boolean allExists(List<Path> dirs) {
        boolean exists = true;
        for (Path path : dirs) {
            if (!Files.exists(path)) {
                exists = false;
                break;
            }
        }
        return exists;
    }
}