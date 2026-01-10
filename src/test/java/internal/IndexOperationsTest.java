package internal;

import command.commands.add.Add;
import command.commands.init.Init;
import filesystem.FileSystem;;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static filesystem.utils.FileSystemTestUtils.*;
import static filesystem.utils.TestData.RESOURCES_DIR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IndexOperationsTest {
    private static final Path rootDir = Path.of(RESOURCES_DIR + "/" + "indexOperationsDir");
    private final FileSystem fileSystem = new FileSystem(() -> rootDir);
    private static Add add;
    private static Init init;

    @BeforeAll
    public static void initCommands() {
        init = new Init(() -> rootDir, System.out::println);
        add = new Add(() -> rootDir, System.out::println);
    }

    @BeforeEach
    public void createLocalRootFolder() {
        createDirIfNotExists(rootDir);
    }

    @AfterEach
    public void deleteLocalRootFolder() {
        recursivelyRemoveDirectory(rootDir);
    }

    @Test
    public void addEmptyFileToIndex() {
        String fileName = "fileInIndex";
        createFile(Path.of(rootDir + "/" + fileName));
        init.execute();
        Assertion.checkIndexFileEmpty(fileSystem);
        add.execute(fileName);
        checkFileAddedToIndex(fileName);
    }

    @Test
    public void addNonEmptyFileToIndex() {
        String fileName = "fileInIndexNotEmpty";
        createFile(Path.of(rootDir + "/" + fileName));
        appendToFile(Path.of(rootDir + "/" + fileName), "content");
        init.execute();
        Assertion.checkIndexFileEmpty(fileSystem);
        add.execute(fileName);
        checkFileAddedToIndex(fileName);
    }

    @Test
    public void addAddedFileToIndex() {

    }

    @Test
    public void addSeveralFilesToIndex() {
    }

    @Test
    public void editAndAddSeveralTimes() {
        String fileName = "twiceEditedFile";
        Path filePath = Path.of(rootDir + "/" + fileName);
        createFile(filePath);
        appendToFile(filePath, "content");
        init.execute();
        Assertion.checkIndexFileEmpty(fileSystem);
        add.execute(fileName);
        checkFileAddedToIndex(fileName);
        appendToFile(filePath, "content2");
        add.execute(fileName);
        checkFileAddedToIndex(fileName);
        appendToFile(filePath, "content3");
        add.execute(fileName);
        checkFileAddedToIndex(fileName);

    }

    //MORE TESTS TO IMPLEMENT:
    //init non empty repo and add
    //init an empty repo and add
    //add to index a whole folder with files

    private void checkFileAddedToIndex(String fileName) {
        Assertion.fileAddedToIndexFile(fileSystem, fileName);
        Assertion.fileAddedToIndexFolder(fileSystem, fileName);
    }

    private void createInRoot(String fileName) {
        createFile(Path.of(rootDir + "/" + Path.of(fileName)));
    }

    private void createInRootAndAppend(String fileName, String fileContent) {
        createInRoot(fileName);
        appendToFile(Path.of(rootDir + "/" + fileName), fileContent);

    }

}
