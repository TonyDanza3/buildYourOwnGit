package filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static filesystem.FileUtils.*;

public class FileEditor {

    public void appendToFile(Path filePath, String str) {
        try {
            Files.writeString(filePath, str, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Could not append to file " + filePath + " : " + e);
        }
    }

    public FileEditor replaceLine(Path path, int lineNumber, String newValue) {
        checkIfFile(path);
        checkIfExceedsFileSize(path, lineNumber);
        try (Stream<String> lines = Files.lines(path)) {
            ArrayList<String> linesList = new ArrayList<>(lines.toList());
            linesList.set(lineNumber - 1, newValue);
            writeToFile(path, linesList);
        } catch (IOException e) {
            throw new RuntimeException("Could not replace line " + lineNumber + " in file " + path + " :" + e);
        }
        return this;
    }

    public void writeToFile(Path path, List<String> lines) {
        try {
            Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file " + path + ": " + e);
        }
    }

    public void replaceWholeFile(Path filePath, String str) {
    }

    public void clearFile() {
    }
}
