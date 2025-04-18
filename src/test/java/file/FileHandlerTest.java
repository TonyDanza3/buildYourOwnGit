package file;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class FileHandlerTest {
    private final String resourcesDir = "src/test/resources";
    private final FileHandler fileHandler = new FileHandler();


    /*
    Corner cases:
        fileAlreadyExists
        directory does not exist
     */
    @Test
    public void createNewFile() {
        String folder = resourcesDir + "/createdFiles";
        Path file = Paths.get(folder + "/newTestFile");
//        assertThat(createNewDir(folder))
//                .as("Could not create test directory")
//                .isTrue();
//        assertThat(fileExists(file))
//                .as("Test file should not yet be created")
//                .isFalse();
        fileHandler.createFile(file);
        assertThat(fileExists(file))
                .as("Test file is not created")
                .isTrue();
    }


    private boolean fileExists(Path filePath) {
        File file = new File(filePath.toString());
        if (file.isDirectory()) {
            throw new RuntimeException(filePath + " is expected to be a file but it is a directory");
        }
        return file.exists();
    }

    private boolean directoryExists(Path directory) {
        File file = new File(directory.toString());
        if (file.isFile()) {
            throw new RuntimeException(directory + " is expected to be a directory but it is a file");
        }
        return file.exists();
    }

    private boolean createNewDir(String dir) {
        return new File(dir).mkdir();
    }
}
