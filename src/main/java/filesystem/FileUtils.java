package filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FileUtils {

    public static int countLines(Path path) {
        checkIfFile(path);
        try {
            return Files.lines(path).toList().size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkIfFile(Path path) {
        if (!new File(String.valueOf(path)).isFile()) {
            throw new RuntimeException(path + " is not a file");
        }
    }

    public static void checkIfExceedsFileSize(Path path, int lineNumber) {
        checkIfFile(path);
        int fileLinesAmount;
        try {
            fileLinesAmount = Files.lines(path).toList().size();
        } catch (IOException e) {
            throw new RuntimeException("Could not get lines amount of file " + path + ": ", e);
        }
        if (lineNumber > fileLinesAmount - 1) {
            throw new RuntimeException("Specified line number '" + lineNumber + "' exceeds file lines amount");
        }
    }

    public static String getDirectoryFromPath(Path dir) {
        List<String> tokens = List.of(dir.toString().split("/"));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tokens.size() - 1; i++) {
            result.append(tokens.get(i));
            result.append("/");
        }
        return result.toString();
    }

    public static String getFileNameFromPath(Path dir) {
        List<String> tokens = List.of(dir.toString().split("/"));
        return tokens.get(tokens.size() - 1);
    }
}