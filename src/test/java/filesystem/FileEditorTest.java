package filesystem;

import filesystem.utils.FileSystemTestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import filesystem.utils.Assertion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static filesystem.utils.FileSystemTestUtils.*;
import static filesystem.utils.TestData.*;

public class FileEditorTest {
    
    private final FileEditor fileEditor = new FileEditor();

    @BeforeAll
    public static void prepareFiles() {
        createDirIfNotExists(FILE_EDITOR_DIRECTORY);
        createFile(FILE_ONE);
        createFile(FILE_TWO);
        writeToFile(FILE_ONE, MAIN_METHOD_UNFORMATTED);
    }

    @AfterAll
    public static void clearDirs() {
        recursivelyRemoveDirectory(FILE_EDITOR_DIRECTORY);
    }

    @Test
    public void replaceLineInTheMiddle() {
        fileEditor.replaceLine(FILE_ONE, 2, "    public static void main(String[] args) {")
                .replaceLine(FILE_ONE, 3, "    }");
        Assertion.fileContentsEqualTo(FILE_ONE, MAIN_METHOD_FORMATTED);
    }


    @Test
    public void replaceLineInEmptyFile() {
        Path emptyFilePath = Path.of(FILE_EDITOR_DIRECTORY + "/" + "emptyFile");
        String addedString = "New Line!";
        createFile(emptyFilePath);
        fileEditor.replaceLine(emptyFilePath, 1, addedString);
        Assertion.fileContentsEqualTo(emptyFilePath,addedString);

    }

    @Test
    public void replaceContentOfEmptyFile() {
        String expected = MAIN_METHOD_FORMATTED + "\n\n" + ADDITIONAL_METHOD;
        fileEditor.replaceFileContents(FILE_TWO, expected);
        Assertion.fileContentsEqualTo(FILE_TWO, expected);
    }

    @Test
    public void appendToFile() {
        String initialFileContents = fileToString(FILE_ONE);
        String expected = initialFileContents + MAIN_METHOD_UNFORMATTED;
        fileEditor.appendToFile(FILE_ONE, MAIN_METHOD_UNFORMATTED);
        Assertion.fileContentsEqualTo(FILE_ONE, expected);

    }

    @Test
    public void editDirectory() {
        assertThrows(RuntimeException.class, () -> fileEditor.replaceLine(FILE_EDITOR_DIRECTORY, 2, "new"));
    }

    @Test
    public void exceededLineNumber() {
        assertThrows(RuntimeException.class, () -> fileEditor.replaceLine(FILE_ONE, 100500, "kobasica"));
    }

    private String fileToString(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}