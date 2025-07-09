package internal;

import command.commands.init.Init;
import filesystem.FileSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static constant.Names.GIT_FOLDER_NAME;
import static constant.Names.OBJECTS_FOLDER_SUB_DIR;
import static filesystem.utils.TestData.RESOURCES_DIR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PseudoBlobTest {

    private final Path rootDir = Path.of(RESOURCES_DIR + "/" + "pseudoBlobRepoDir");
    private static final FileSystem fileSystem = new FileSystem();
    private final Init init = new Init(() -> rootDir, System.out::println);

    @BeforeAll
    public static void createRootDirectory() {
        fileSystem.createDirectory(Path.of(RESOURCES_DIR + "/" + "pseudoBlobRepoDir"));
    }

    @Test
    public void createPseudoBlobFile() {
        init.execute();
        String[] subfoldersAndFiles = new File(rootDir + "/" + OBJECTS_FOLDER_SUB_DIR).list();
        for (String st: subfoldersAndFiles) {
            System.out.println("subf" + st);
        }
        assertThat(subfoldersAndFiles.length == 0)
                .withFailMessage("Expected amount of subfolders is 0, but actual is " + subfoldersAndFiles.length)
                .isTrue();




    }
}
