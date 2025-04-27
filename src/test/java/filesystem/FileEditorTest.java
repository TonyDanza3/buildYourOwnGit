package filesystem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.FileTestUtils.*;

public class FileEditorTest {

    private static final String fileEditorDirectory = "src/test/resources/fileEditor";
    private static final Path fileOne = Path.of(fileEditorDirectory + "/one");
    private static final Path fileTwo = Path.of(fileEditorDirectory + "/two");
    private final FileEditor fileEditor = new FileEditor();
    private static final String mainMethodUnformatted = "public class Main {" +
            "\npublic static void main(String[] args) {" +
            "\n}" +
            "\n}";
    private static final String mainMethodFormatted = "public class Main {" +
            "\n    public static void main(String[] args) {" +
            "\n    }" +
            "\n}";
    private static final String additionalMethod = "    public void removeDirectoryRecursively() {\n" +
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

    @BeforeAll
    public static void prepareFiles() {
        createDirIfNotExists(fileEditorDirectory);
        createFile(fileOne);
        createFile(fileTwo);
        writeToFile(fileOne, mainMethodUnformatted);

    }

    @Test
    public void replaceLineInTheMiddle() {
        fileEditor.replaceLine(fileOne, 2, "    public static void main(String[] args) {")
                .replaceLine(fileOne, 3, "    }");
        try {
            String actualFileContents = new String(Files.readAllBytes(fileOne));
            assertThat(fileContainsIsEqualTo(actualFileContents, mainMethodFormatted))
                    .withFailMessage("File " + fileOne + " was not edited properly. Result file contents is: \n" + actualFileContents
                    + "\nbut expected \n" + mainMethodFormatted)
                    .isTrue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: think about replaceInTheBeginning and inTheEnd tests

    @Test
    public void writeToEmptyFile() {

    }

    @Test
    public void appendToFile() {
    }

    @Test
    public void editDirectory() {
    }

    private boolean fileContainsIsEqualTo(String fileContents, String equalTo) {
        return fileContents.trim().equals(equalTo.trim());
    }
}
