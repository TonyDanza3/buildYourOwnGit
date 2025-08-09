package filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
            if(countLines(path) == 0) {
                appendToFile(path, newValue);
            }
            ArrayList<String> linesList = new ArrayList<>(lines.toList());
            linesList.set(lineNumber - 1, newValue);
            replaceFileContents(path, composeFileContentsWithoutTrailingNextLine(linesList));
        } catch (IOException e) {
            throw new RuntimeException("Could not replace line " + lineNumber + " in file " + path + " :" + e);
        }
        return this;
    }

    public void replaceFileContents(Path path, String content) {
        try {
            Files.writeString(path, content, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file " + path + ": " + e);
        }
    }

    private String composeFileContentsWithoutTrailingNextLine(List<String> strings) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            if (i == strings.size() - 1) {
                result.append(strings.get(i));
            } else {
                result.append(strings.get(i));
                result.append("\n");
            }
        }
        return result.toString();
    }
}
