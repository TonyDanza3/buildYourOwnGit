package internal;

import filesystem.FileSystem;

import java.io.File;
import java.nio.file.Path;

public class PseudoBlob {

    public static void generateAndPlacePseudoBlobFile(FileSystem fileSystem, Path targetDir, File initialFile) {
        String fileContents = fileSystem.getFileContentsAsString(initialFile.toPath());
        fileSystem.createFile(targetDir);
        String blobContents = "blob " +
                fileContents.length() +
                " " +
                "\\0" +
                fileContents;
        fileSystem.putContentToFile(targetDir, blobContents);
    }
}
