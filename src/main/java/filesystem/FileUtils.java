package filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        if (! new File(String.valueOf(path)).isFile()) {
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
        if(lineNumber > fileLinesAmount - 1) {
            throw new RuntimeException("Specified line number '" + lineNumber + "' exceeds file lines amount");
        }
    }
}
