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

//TODO: cover with tests
    public void createFileInGitSubdirectory(Path path) {
        Path gitSubdirectory = Path.of(FileUtils.getDirectoryFromPath(path) + "/.git");
        Path fileName = FileUtils.getFileNameFromPath(path);
        fileManager.createFile(Path.of(gitSubdirectory + "/" + fileName));
    }

    public void putContentToFile(Path file, String content) {
        createFile(file);
        fileEditor.replaceFileContents(file, content);
    }

//TODO cover with tests
    public void createDirectory(Path path) {
        directoryManager.createDirectory(path);
    }
    public void deleteFile(Path path) {
        fileManager.deleteFile(path);
    }

    public void deleteDirectory(Path path) {
        directoryManager.removeDirectory(path);
    }

    public boolean hasFileOrDirectory(Path path, String fileName) {
        return directoryManager.containsFile(path, fileName);
    }
}