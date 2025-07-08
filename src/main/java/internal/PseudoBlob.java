package internal;

import filesystem.FileSystem;

import java.nio.file.Path;

public class PseudoBlob {
    public static void generatePseudoBlobFile(FileSystem fileSystem, Path filePath, String fileContents) {
        fileSystem.createFile(filePath);
        String blobContents = "blob " +
                fileContents.length() +
                " " +
                "\\0" +
                fileContents;
        fileSystem.putContentToFile(filePath, blobContents);
    }
}
