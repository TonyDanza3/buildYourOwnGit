package filesystem;

import java.nio.file.Path;

public class FileSystem {
    private final FileManager fileManager = new FileManager();
    private final FileEditor fileEditor = new FileEditor();
    private final DirectoryManager directoryManager = new DirectoryManager();

    public void createFile(Path path) {
        directoryManager.createDirectoryIfNotExists(FileUtils.getDirectoryFromPath(path));
        fileManager.createFile(path);
    }
//TODO
    public void putContentToFile(Path file, String content) {
        createFile(file);
        fileEditor.replaceFileContents(file, content);
    }

    public void deleteFile(Path path) {
        fileManager.createFile(path);
    }
    public void deleteDirectory(Path path) {
        directoryManager.removeDirectory(path);
    }
}