package file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileHandlerTest {
    private static final String resourcesDir = "src/test/resources";
    private static final String createdFilesFolder = resourcesDir + "/createdFiles";
    private static final Path newTestFile = Paths.get(createdFilesFolder + "/newTestFile");
    private static final Path duplicateFile = Paths.get(createdFilesFolder + "/duplicateFile");
    private static final String nonExistentDirectory = resourcesDir + "/createdFiles/nonExistentDirectory";
    private final FileHandler fileHandler = new FileHandler();

    @AfterAll
    public static void removeDirs() {
        removeDirectory(Paths.get(createdFilesFolder));
        removeDirectory(newTestFile);
        removeDirectory(duplicateFile);
        removeDirectory(Paths.get(nonExistentDirectory));
    }

    @Test
    public void createNewFile() {
        createDirIfNotExists(createdFilesFolder);
        assertThat(fileExists(newTestFile))
                .as("Test file should not yet be created")
                .isFalse();
        fileHandler.createFile(newTestFile);
        assertThat(fileExists(newTestFile))
                .as("Test file is not created")
                .isTrue();
    }

    @Test
    public void createDuplicateFile() {
        createDirIfNotExists(createdFilesFolder);
        fileHandler.createFile(duplicateFile);
        assertThrows(RuntimeException.class, () -> fileHandler.createFile(duplicateFile));
    }

    @Test
    public void createFileInNonExistentDirectory() {
        Path fileDir = Paths.get(nonExistentDirectory + "/someFile");
        assertThrows(RuntimeException.class, () -> fileHandler.createFile(fileDir));
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

    private void createDirIfNotExists(String dir) {
        if (!directoryExists(Paths.get(dir))) {
            new File(dir).mkdir();
        }
    }

    private static void removeDirectory(Path path) {
        File directoryToBeDeleted = new File(path.toString());
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                removeDirectory(Paths.get(file.getPath()));
            }
        }
        directoryToBeDeleted.delete();
//        File[] files = new File(path.toString()).listFiles();
//        for(File file: files) {
//            file.delete();
//        }
//        try {
//            Files.delete(path);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}