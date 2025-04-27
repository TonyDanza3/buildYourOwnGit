package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.FileTestUtils.*;

public class DirectoryManagerTest {

    private static final String resourcesDir = "src/test/resources";
    private static final Path createdDir = Path.of(resourcesDir + "/directoryManager");
    private static final Path removedDir = Path.of(resourcesDir + "/directoryToRemove");
    private static final Path recDir = Path.of(resourcesDir + "/directoryRec");
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
    }

    @Test
    public void createDirectory() {
        assertThat(directoryExists(createdDir))
                .withFailMessage("Directory " + createdDir + " exists but it should not")
                .isFalse();
        directoryManager.createDirectory(createdDir);
        assertThat(directoryExists(createdDir))
                .withFailMessage("Directory " + createdDir + " does not exist but it should have been created")
                .isTrue();
    }

    @Test
    public void removeDirectory() {
        directoryManager.createDirectory(removedDir);
        assertThat(directoryExists(removedDir))
                .withFailMessage("Directory " + removedDir + " does not exist but it should have been created")
                .isTrue();
        directoryManager.removeDirectory(removedDir);
        assertThat(directoryExists(removedDir))
                .withFailMessage("Directory " + removedDir + " still exists but should have been removed")
                .isFalse();
    }

    @Test
    public void removeDirectoryRecursively() {
        directoryManager.createDirectory(recDir);
        childRecDirs.forEach(dir -> directoryManager.createDirectory(dir));
        assertThat(directoryExists(recDir))
                .withFailMessage("Directory " + removedDir + " does not exist but it should have been created")
                .isTrue();
        assertThat(allExists(childRecDirs)).isTrue();
        directoryManager.removeDirectory(recDir);
        assertThat(directoryExists(recDir))
                .withFailMessage("Directory " + recDir + " still exists but should have been removed")
                .isFalse();
        assertThat(allExists(childRecDirs)).isFalse();
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