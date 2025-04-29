package filesystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @AfterAll
    public static void clearDirs() {
        recursivelyRemoveDirectory(Path.of(fileEditorDirectory));
    }

    @Test
    public void replaceLineInTheMiddle() {
        fileEditor.replaceLine(fileOne, 2, "    public static void main(String[] args) {")
                .replaceLine(fileOne, 3, "    }");
        String actualFileContents = fileToString(fileOne);
        assertThat(fileContentsIsEqualTo(actualFileContents, mainMethodFormatted))
                .withFailMessage(
                        "File " + fileOne + " was not edited properly. Result file contents is: \n"
                                + actualFileContents
                                + "\nbut expected \n" + mainMethodFormatted)
                .isTrue();
    }

    //TODO: think about replaceInTheBeginning and inTheEnd tests

    @Test
    public void writeToEmptyFile() {
        String expected = mainMethodFormatted + "\n\n" + additionalMethod;
        fileEditor.writeToFile(fileTwo, List.of(expected));
        assertThat(fileContentsIsEqualTo(fileTwo, expected))
                .isTrue();
    }

    @Test
    public void appendToFile() {
        String initialFileContents = fileToString(fileOne);
        String expected = initialFileContents + mainMethodUnformatted;
        fileEditor.appendToFile(fileOne, mainMethodUnformatted);
        assertThat(fileContentsIsEqualTo(fileTwo, expected)).isTrue();

    }

    @Test
    public void editDirectory() {
        assertThrows(RuntimeException.class, () -> fileEditor.replaceLine(Path.of(fileEditorDirectory), 2, "new"));
    }

    @Test
    public void exceededLineNumber() {
        assertThrows(RuntimeException.class, () -> fileEditor.replaceLine(fileOne, 100500, "kobasica"));
    }

    private boolean fileContentsIsEqualTo(String fileContents, String equalTo) {
        return fileContents.trim().equals(equalTo.trim());
    }

    private String fileToString(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean fileContentsIsEqualTo(Path file, String equalTo) {
        try {
            String fileContents = new String(Files.readAllBytes(file));
            return fileContents.equals(equalTo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}