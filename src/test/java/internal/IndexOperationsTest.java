package internal;

import command.commands.add.Add;
import command.commands.init.Init;
import filesystem.FileSystem;;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.util.List;

import static command.commands.init.GitFolders.OBJECTS_INFO;
import static command.commands.init.GitFolders.OBJECTS_PACK;
import static constant.Names.GIT_FOLDER_NAME;
import static constant.Names.OBJECTS_FOLDER_SUB_DIR;
import static filesystem.utils.FileSystemTestUtils.*;
import static filesystem.utils.TestData.RESOURCES_DIR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IndexOperationsTest {
    private static final Path rootDir = Path.of(RESOURCES_DIR + "/" + "indexOperationsDir");
    private final FileSystem fileSystem = new FileSystem(() -> rootDir);


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
        Init init = new Init(() -> rootDir, System.out::println);
        Add add = new Add(() -> rootDir, System.out::println);
        Path fileName = Path.of("fileInIndex");
        createFile(Path.of(rootDir + "/" + fileName));
        init.execute();
        Assertion.checkIndexFileEmpty(fileSystem);
        add.execute(String.valueOf(fileName));
        Assertion.fileAddedToIndexFile(fileSystem, fileName);
        Assertion.fileAddedToIndexFolder(fileSystem, fileName);
    }

    @Test
    public void addNonEmptyFileToIndex() {

    }

    @Test
    public void addAddedFileToIndex() {}

    @Test
    public void addSeveralFilesToIndex() {}

}
