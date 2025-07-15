package internal;

import command.commands.init.Init;
import filesystem.FileSystem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static constant.Names.GIT_FOLDER_NAME;
import static constant.Names.OBJECTS_FOLDER_SUB_DIR;
import static filesystem.utils.FileSystemTestUtils.recursivelyRemoveDirectory;
import static filesystem.utils.TestData.RESOURCES_DIR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PseudoBlobTest {

    private static final Path rootDir = Path.of(RESOURCES_DIR + "/" + "pseudoBlobRepoDir");
    private static final FileSystem fileSystem = new FileSystem();
    private final Init init = new Init(() -> rootDir, System.out::println);

    @BeforeAll
    public static void createRootDirectory() {
        fileSystem.createDirectory(rootDir);
    }

    @AfterAll
    public static void removeDirectory() {
        recursivelyRemoveDirectory(rootDir);
    }

    @Test
    public void createPseudoBlobFile() {
        Path objectFolder = Path.of(rootDir + "/" + OBJECTS_FOLDER_SUB_DIR);
        Path initialFilePath = Path.of(rootDir + "/ oneFile");
        Path resultingBlobPath = Path.of(objectFolder + "/" + "blobFile");
        fileSystem.createFile(initialFilePath);
        fileSystem.putContentToFile(initialFilePath, "Content is sooo content");
        PseudoBlob.generatePseudoBlobFile(fileSystem, resultingBlobPath, new File(String.valueOf(initialFilePath)));
        String resultingString = fileSystem.getFileContentsAsString(resultingBlobPath);
        String[] resultingStringTokenized = resultingString.split(" ");
        assertThat(resultingStringTokenized[0].equals("blob"))
                .withFailMessage("Incorrectly generated blob: first token of blob file != 'blob'")
                .isTrue();
        assertThat(resultingStringTokenized[1].equals("23"))
                .withFailMessage("Incorrectly generated blob: second token of blob file != '23'")
                .isTrue();
        assertThat(resultingStringTokenized[2].equals("\\0Content"))
                .withFailMessage("Incorrectly generated blob: third token of blob file != '\\0Content'")
                .isTrue();
        String contentLeftover = resultingStringTokenized[3] + " " + resultingStringTokenized[4] + " " + resultingStringTokenized[5];
        assertThat(contentLeftover.equals("is sooo content"))
                .as("Incorrectly generated blob: file content is incorrect: expected '" + contentLeftover + "' but was + 'is too content'")
                .isTrue();

    }

    //TODO: to be implemented as well
    @Test
    public void createPseudoBlobFromEmptyFile() {

    }


}
