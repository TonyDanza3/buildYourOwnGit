package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileSystemTestUtils {

    public static void createDirIfNotExists(Path dir) {
        if (!checkDirectoryExists(dir)) {
            new File(String.valueOf(dir)).mkdir();
        }
    }

    public static void createFile(Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not create file " + path + " :" + e);
        }
    }

    public static void recursivelyRemoveDirectory(Path filePath) {
        File[] directoryToDelete = new File(filePath.toString()).listFiles();
        if(directoryToDelete != null) {
            for (File file : directoryToDelete) {
                recursivelyRemoveDirectory(Paths.get(file.getPath()));
            }
        }
        new File(filePath.toString()).delete();
    }

    public static boolean checkDirectoryExists(Path directory) {
        File file = new File(directory.toString());
        if (file.isFile()) {
            throw new RuntimeException(directory + " is expected to be a directory but it is a file");
        }
        return file.exists();
    }

    public static boolean checkFileExists(Path filePath) {
        File file = new File(filePath.toString());
        if (file.isDirectory()) {
            throw new RuntimeException(filePath + " is expected to be a file but it is a directory");
        }
        return file.exists();
    }

    public static void writeToFile(Path path, String string) {
        try {
            Files.writeString(path, string, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean fileContentsIsEqualTo(Path file, String equalTo) {
        try {
            String fileContents = new String(Files.readAllBytes(file));
            return fileContents.equals(equalTo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean fileContentsIsEqualTo(String fileContents, String equalTo) {
        return fileContents.trim().equals(equalTo.trim());
    }
}