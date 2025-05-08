package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import utils.Assertion;
import java.nio.file.Path;

import static utils.FileTestUtils.*;

public class FileSystemTest {

    private final FileSystem fileSystem = new FileSystem();
    private static final String fileSystemRootDir = "src/test/resources/fileSystem";
    private static final Path fileWithContents = Path.of(fileSystemRootDir + "/fileWithContents");
    private static final Path nonexistentFile = Path.of(fileSystemRootDir + "/nonexistentFile");
    private static final String fileContents = "    public void removeDirectoryRecursively() {\n" +
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
        Assertion.directoryExists(coreEngineFolder, utilsFolder);
        Assertion.fileExists(coreEngineFile, utilsFile);
    }

    @Test
    public void createDuplicateFile() {
        Path duplicateFile = Path.of(fileSystemRootDir + "/duplicateFile");
        fileSystem.createFile(duplicateFile);
        Assertion.fileExists(duplicateFile);
        fileSystem.createFile(duplicateFile);
        Assertion.fileExists(duplicateFile);
    }

    @Test
    public void duplicateDoesNotRewriteFileContents() {
        fileSystem.createFile(fileWithContents);
        fileSystem.putContentToFile(fileWithContents, fileContents);
        Assertion.fileExists(fileWithContents);
        Assertion.fileContentsEqualTo(fileWithContents, fileContents);
        fileSystem.createFile(fileWithContents);
        Assertion.fileExists(fileWithContents);
        Assertion.fileContentsEqualTo(fileWithContents, fileContents);
    }

    @Test
    public void fillNonExistentFileWithContents() {
        fileSystem.putContentToFile(nonexistentFile, fileContents);
        Assertion.fileExists(nonexistentFile);
        Assertion.fileContentsEqualTo(nonexistentFile, fileContents);
    }
}