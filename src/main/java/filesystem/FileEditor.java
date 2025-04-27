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

    public List<String> replaceLine(Path path, int lineNumber, String newValue) throws IOException {
        checkIfFile(path);
        checkIfExceedsFileSize(path, lineNumber);
        try (Stream<String> lines = Files.lines(path)) {
            ArrayList<String> linesList = new ArrayList<>(lines.toList());
            linesList.add(lineNumber, newValue);
            return linesList;
        }
    }

    public void writeToFile(Path path, List<String> lines) {
        StringBuilder builder = new StringBuilder();
        lines.forEach(line -> {
            builder.append(line);
            builder.append("\n");
        });
        try {
            Files.writeString(path, builder.toString(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file " + path + ": " + e);
        }
    }

//    public List<String> replaceLine(List<String> lines, int lineNumber, String newValue) {
//        lines.add(lineNumber, newValue);
//        return lines;
//    }


    public void replaceWholeFile(Path filePath, String str) {
    }

    public void clearFile() {
    }
}
