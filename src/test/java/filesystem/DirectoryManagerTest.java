package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import utils.Assertion;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.FileTestUtils.*;
import static utils.TestData.*;

public class DirectoryManagerTest {
    
    private List<Path> childRecDirs = List.of(
            Path.of(DIR_WITH_CHILDS + "/one"),
            Path.of(DIR_WITH_CHILDS + "/two"),
            Path.of(DIR_WITH_CHILDS + "/three"),
            Path.of(DIR_WITH_CHILDS + "/one/one"),
            Path.of(DIR_WITH_CHILDS + "/one/two"),
            Path.of(DIR_WITH_CHILDS + "/one/three")
    );
    DirectoryManager directoryManager = new DirectoryManager();

    @AfterAll
    public static void clearTestDirectories() {
        recursivelyRemoveDirectory(CREATED_DIR);
        recursivelyRemoveDirectory(REMOVED_DIR);
        recursivelyRemoveDirectory(DIR_WITH_CHILDS);
        recursivelyRemoveDirectory(DUPLICATE_DIR);
        recursivelyRemoveDirectory(IDEMPOTENT_DUPLICATE_DIR);
        recursivelyRemoveDirectory(Path.of(RESOURCES_DIR + "/levelOne"));
        recursivelyRemoveDirectory(CONTAINS_FILE_DIR);
    }

    @Test
    public void createDirectory() {
        Assertion.directoryNotExists(CREATED_DIR);
        directoryManager.createDirectory(CREATED_DIR);
        Assertion.directoryExists(CREATED_DIR);
    }

    @Test
    public void removeDirectory() {
        directoryManager.createDirectory(REMOVED_DIR);
        Assertion.directoryExists(REMOVED_DIR);
        directoryManager.removeDirectory(REMOVED_DIR);
        Assertion.directoryNotExists(REMOVED_DIR);
    }

    @Test
    public void CREATED_DIRectoryOnDeepNestingLevel() {
        directoryManager.createDirectory(DEEP_NESTING_LEVEL_DIR);
        Assertion.directoryExists(DEEP_NESTING_LEVEL_DIR);
    }

    @Test
    public void removeDirectoryOnDeepNestingLevel() {
        createDirIfNotExists(String.valueOf(DEEP_NESTING_LEVEL_DIR));
        Path levelOneDir = Path.of(RESOURCES_DIR + "/levelOne");
        Path dirOne = Path.of(RESOURCES_DIR + "/levelOne/OtherDir");
        Path dirTwo = Path.of(RESOURCES_DIR + "/levelOne/OtherDir2");
        Path file = Path.of(RESOURCES_DIR + "/levelOne/OtherDir2/File2");
        directoryManager.createDirectory(dirOne);
        directoryManager.createDirectory(dirTwo);
        createFile(file);
        writeToFile(file, FILE_CONTENTS);

        Assertion.directoryExists(dirOne);
        Assertion.directoryExists(dirTwo);
        Assertion.fileExists(file);
        assertThat(fileContentsIsEqualTo(file, FILE_CONTENTS))
                .withFailMessage("File contents does not match to : \n" + FILE_CONTENTS)
                .isTrue();
        directoryManager.removeDirectory(levelOneDir);
        Assertion.directoryNotExists(dirOne);
        Assertion.directoryNotExists(dirTwo);
        Assertion.directoryNotExists(levelOneDir);
        Assertion.fileNotExists(file);
    }

    @Test
    public void createdDuplicateDirectory() {
        Assertion.directoryNotExists(DUPLICATE_DIR);
        directoryManager.createDirectory(DUPLICATE_DIR);
        Assertion.directoryExists(DUPLICATE_DIR);
        directoryManager.createDirectory(DUPLICATE_DIR);
        Assertion.directoryExists(DUPLICATE_DIR);
    }

    @Test
    public void createIfNotExistDuplicateDirectory() {
        Assertion.directoryNotExists(IDEMPOTENT_DUPLICATE_DIR);
        directoryManager.createDirectoryIfNotExists(IDEMPOTENT_DUPLICATE_DIR);
        Assertion.directoryExists(IDEMPOTENT_DUPLICATE_DIR);
        directoryManager.createDirectoryIfNotExists(IDEMPOTENT_DUPLICATE_DIR);
        Assertion.directoryExists(IDEMPOTENT_DUPLICATE_DIR);
    }

    @Test
    public void removeDirectoryRecursively() {
        directoryManager.createDirectory(DIR_WITH_CHILDS);
        childRecDirs.forEach(dir -> directoryManager.createDirectory(dir));
        Assertion.directoryExists(DIR_WITH_CHILDS);
        Assertion.directoryExists(childRecDirs);
        directoryManager.removeDirectory(DIR_WITH_CHILDS);
        Assertion.directoryNotExists(childRecDirs);
        Assertion.directoryNotExists(DIR_WITH_CHILDS);
    }

    @Test
    public void containsFileFullMatch() {
        directoryManager.createDirectoryIfNotExists(CONTAINS_FILE_DIR);
        createFile(Path.of(CONTAINS_FILE_DIR + "/pom.xml"));
        assertThat(directoryManager.containsFile(CONTAINS_FILE_DIR, "pom.xml"))
                .withFailMessage("Directory" + CONTAINS_FILE_DIR + " should have contain file 'pom.xml'" )
                .isTrue();
    }

    @Test
    public void argumentAsSubstringOfFileName() {
        directoryManager.createDirectoryIfNotExists(CONTAINS_FILE_DIR);
        createFile(Path.of(CONTAINS_FILE_DIR + "/some.xml"));
        assertThat(directoryManager.containsFile(CONTAINS_FILE_DIR, "some.xm"))
                .withFailMessage("Directory" + CONTAINS_FILE_DIR + " should not have contain file 'some.xm'")
                .isFalse();
    }

    @Test
    public void fileNameAsSubstringOfArgument() {
        directoryManager.createDirectoryIfNotExists(CONTAINS_FILE_DIR);
        createFile(Path.of(CONTAINS_FILE_DIR + "/someOther.xml"));
        assertThat(directoryManager.containsFile(CONTAINS_FILE_DIR, "someOther.xmls"))
                .withFailMessage("Directory" + CONTAINS_FILE_DIR + " should not have contain file 'someOther.xmls'")
                .isFalse();
    }

    @Test
    public void sameExtension() {
        directoryManager.createDirectoryIfNotExists(CONTAINS_FILE_DIR);
        createFile(Path.of(CONTAINS_FILE_DIR + "/one.xls"));
        assertThat(directoryManager.containsFile(CONTAINS_FILE_DIR, "ona.xls"))
                .withFailMessage("Directory" + CONTAINS_FILE_DIR + " should not have contain file 'ona.xls'")
                .isFalse();
    }
}