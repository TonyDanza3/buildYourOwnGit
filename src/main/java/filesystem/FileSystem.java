package filesystem;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSystem {
    private final FileManager fileManager = new FileManager();
    private final FileEditor fileEditor = new FileEditor();
    private final DirectoryManager directoryManager = new DirectoryManager();
    public Path currentDirectory;
    @Getter
    Supplier<Path> currentDirectorySupplier;

    public FileSystem (Supplier<Path> currentDirectorySupplier) {
        this.currentDirectorySupplier = currentDirectorySupplier;
        currentDirectory = currentDirectorySupplier.get();
    }

    public FileSystem () {
        currentDirectory = Path.of(System.getProperty("user.dir"));
    }

    //TODO: createFile() must take String fileName as an argument
    public void createFile(Path path) {
        directoryManager.createDirectoryIfNotExists(FileUtils.getDirectoryFromPath(path));
        fileManager.createFile(path);
    }

    //TODO: cover with tests
    public void createFileInGitSubdirectory(String fileName) {
        Path gitSubdirectory = Path.of(currentDirectory + "/.git");
        fileManager.createFile(Path.of(gitSubdirectory + "/" + fileName));
    }

    public void putContentToFile(Path file, String content) {
        createFile(file);
        fileEditor.replaceFileContents(file, content);
    }

    public void appendLineToFile(Path filePath, String line) {
        fileEditor.appendToFile(filePath, line);
    }

    public void replaceLineInFile(Path path, int lineNumber, String newValue) {
        fileEditor.replaceLine(path, lineNumber, newValue);
    }

    public String getFileContentsAsString(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException("Could not convert contents of " + path + " file to string");
        }
    }

//TODO cover with tests
    public void createDirectory(Path path) {
        directoryManager.createDirectory(path);
    }

    public List<Path> getSubfoldersFromDirectory(Path dir) {
        List<Path> childDirectories = new ArrayList<>();
        try(Stream<Path> allChildObjects = Files.list(dir)) {
            childDirectories = allChildObjects.filter(child -> new File(String.valueOf(child)).isDirectory())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Could not get children of directory " + dir);
        }
        return childDirectories;
    }

    public List<Path> getSubfoldersFromDirectory(Path dir, Path ... excluding) {
        List<Path> childDirectories = new ArrayList<>();
        try(Stream<Path> allChildObjects = Files.list(dir)) {
            childDirectories = allChildObjects.filter(child -> new File(String.valueOf(child)).isDirectory())
                    .filter(child -> Arrays.stream(excluding).noneMatch(excl ->child.equals(excl)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Could not get children of directory " + dir);
        }
        return childDirectories;
    }

    public void createDirectoryInGitSubdirectory(String fileName) {
        Path gitSubdirectory = Path.of(currentDirectory + "/.git");
        directoryManager.createDirectoryIfNotExists(Path.of(gitSubdirectory + "/" + fileName));
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

    public boolean fileOnSuchPathExists(Path path) {
        return hasFileOrDirectory(FileUtils.getDirectoryFromPath(path), String.valueOf(FileUtils.getFileNameFromPath(path)));
    }
}