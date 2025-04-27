package filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileEditor {

    public void appendToFile(Path filePath, String str) {
        try {
            Files.writeString(filePath, str, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Could not append to file " + filePath + " : " + e);
        }
    }

    public void replaceWholeFile(Path filePath, String str) {
    }

    public void clearFile() {
    }
}
