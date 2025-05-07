package filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileManager {

    public void createFile(Path filePath) {
        if (!new File(filePath.toString()).exists()) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create file " + filePath + " : " + e);
            }
        }
    }

    public void deleteFile(Path path) {
        new File(String.valueOf(path)).delete();
    }
}