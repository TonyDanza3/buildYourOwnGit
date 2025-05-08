package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import utils.Assertion;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.FileTestUtils.*;

public class DirectoryManagerTest {

    private final String fileContents = "    public void removeDirectoryRecursively() {\n" +
            "        directoryManager.createDirectory(recDir);\n" +
            "        childRecDirs.forEach(dir -> directoryManager.createDirectory(dir));\n" +
            "        assertThat(directoryExists(recDir))\n" +
            "                .withFailMessage(formatErrMessage(ERRshouldHaveBeenCreated, removedDir))\n" +
            "                .isTrue();\n" +
            "        assertThat(allExists(childRecDirs)).isTrue();\n" +
            "        directoryManager.removeDirectory(recDir);\n" +
            "        assertThat(directoryExists(recDir))\n" +
            "                .withFailMessage(formatErrMessage(ERRshouldHaveBeenRemoved, recDir))\n" +
            "                .isFalse();\n" +
            "        assertThat(allExists(childRecDirs)).isFalse();\n" +
            "    }";
    private static final String resourcesDir = "src/test/resources";
    private static final Path createdDir = Path.of(resourcesDir + "/directoryManager");
    private static final Path removedDir = Path.of(resourcesDir + "/directoryToRemove");
    private static final Path recDir = Path.of(resourcesDir + "/directoryRec");
    private static final Path duplicateDir = Path.of(resourcesDir + "/duplicate");
    private static final Path deepNestingLevelDir = Path.of(resourcesDir + "/levelOne/LevelTwo/LevelThree/LeverFour");
    private static final Path idempotentDuplicateDir = Path.of(resourcesDir + "/idempotentDuplicate");
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
        recursivelyRemoveDirectory(Path.of(resourcesDir + "/levelOne"));
    }

    @Test
    public void createDirectory() {
        Assertion.directoryNotExists(createdDir);
        directoryManager.createDirectory(createdDir);
        Assertion.directoryExists(createdDir);
    }

    @Test
    public void removeDirectory() {
        directoryManager.createDirectory(removedDir);
        Assertion.directoryExists(removedDir);
        directoryManager.removeDirectory(removedDir);
        Assertion.directoryNotExists(removedDir);
    }

    @Test
    public void createdDirectoryOnDeepNestingLevel() {
        directoryManager.createDirectory(deepNestingLevelDir);
        Assertion.directoryExists(deepNestingLevelDir);
    }

    @Test
    public void removeDirectoryOnDeepNestingLevel() {
        createDirIfNotExists(String.valueOf(deepNestingLevelDir));
        Path levelOneDir = Path.of(resourcesDir + "/levelOne");
        Path dirOne = Path.of(resourcesDir + "/levelOne/OtherDir");
        Path dirTwo = Path.of(resourcesDir + "/levelOne/OtherDir2");
        Path file = Path.of(resourcesDir + "/levelOne/OtherDir2/File2");
        directoryManager.createDirectory(dirOne);
        directoryManager.createDirectory(dirTwo);
        createFile(file);
        writeToFile(file, fileContents);

        Assertion.directoryExists(dirOne);
        Assertion.directoryExists(dirTwo);
        Assertion.fileExists(file);
        assertThat(fileContentsIsEqualTo(file, fileContents))
                .withFailMessage("File contents does not match to : \n" + fileContents)
                .isTrue();
        directoryManager.removeDirectory(levelOneDir);
        Assertion.directoryNotExists(dirOne);
        Assertion.directoryNotExists(dirTwo);
        Assertion.directoryNotExists(levelOneDir);
        Assertion.fileNotExists(file);
    }

    @Test
    public void createdDuplicateDirectory() {
        Assertion.directoryNotExists(duplicateDir);
        directoryManager.createDirectory(duplicateDir);
        Assertion.directoryExists(duplicateDir);
        directoryManager.createDirectory(duplicateDir);
        Assertion.directoryExists(duplicateDir);
    }

    @Test
    public void createIfNotExistDuplicateDirectory() {
        Assertion.directoryNotExists(idempotentDuplicateDir);
        directoryManager.createDirectoryIfNotExists(idempotentDuplicateDir);
        Assertion.directoryExists(idempotentDuplicateDir);
        directoryManager.createDirectoryIfNotExists(idempotentDuplicateDir);
        Assertion.directoryExists(idempotentDuplicateDir);
    }

    @Test
    public void removeDirectoryRecursively() {
        directoryManager.createDirectory(recDir);
        childRecDirs.forEach(dir -> directoryManager.createDirectory(dir));
        Assertion.directoryExists(recDir);
        Assertion.directoryExists(childRecDirs);
        directoryManager.removeDirectory(recDir);
        Assertion.directoryNotExists(childRecDirs);
        Assertion.directoryNotExists(recDir);
    }
}