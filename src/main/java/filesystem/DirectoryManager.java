package filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryManager {

    public void createDirectory(Path path) {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDirectoryIfNotExists(Path path) {
        if(!Files.exists(path)) {
            createDirectory(path);
        }
    }

    public void removeDirectory(Path path) {
        File [] childDirs = new File(String.valueOf(path)).listFiles();
        if(childDirs != null) {
            for(File file : childDirs) {
                removeDirectory(Path.of(file.getPath()));
            }
        }
        try {
            Files.delete(path);
        } catch (IOException ignored) {}
    }
}
