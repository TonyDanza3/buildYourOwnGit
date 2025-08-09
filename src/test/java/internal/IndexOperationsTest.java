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
    private final Path gitSubdir = Path.of(rootDir + "/" + GIT_FOLDER_NAME);
    private static final Path objectsDir = Path.of(rootDir + "/" + OBJECTS_FOLDER_SUB_DIR);
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
        Path newFile = Path.of(rootDir + "/" + "fileInIndex");
        createFile(newFile);
        init.execute();
        List<Path> objectsSubfolders = fileSystem.getSubfoldersFromDirectory(objectsDir, Path.of(gitSubdir
                + "/" + OBJECTS_INFO.getFolderName()), Path.of(gitSubdir + "/" + OBJECTS_PACK.getFolderName()));
        assertThat(objectsSubfolders.isEmpty())
                .withFailMessage(objectsDir + " is not empty. It contains the following dirs:\n" + objectsSubfolders)
                .isTrue();
        add.execute(String.valueOf(newFile));
        /*TODO: assert that:
            1 file is placed in objects folder
            2 file is recorded in index file
         */

    }

    @Test
    public void addNonEmptyFileToIndex() {

    }

}
