package utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTestUtils {
    public static void recursivelyRemoveDirectory(Path filePath) {
        File[] directoryToDelete = new File(filePath.toString()).listFiles();
        if(directoryToDelete != null) {
            for (File file : directoryToDelete) {
                recursivelyRemoveDirectory(Paths.get(file.getPath()));
            }
        }
        new File(filePath.toString()).delete();
    }

    public static boolean directoryExists(Path directory) {
        File file = new File(directory.toString());
        if (file.isFile()) {
            throw new RuntimeException(directory + " is expected to be a directory but it is a file");
        }
        return file.exists();
    }
}
