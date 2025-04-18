package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileHandler {

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

    public void createFile(Path filePath) {
        if (new File(filePath.toString()).exists()) {
            throw new RuntimeException("File " + filePath + " already exists");
        }
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create file " + filePath + " : " + e);
        }
    }

    public void createDirectory(Path path) {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile() {
    }
}