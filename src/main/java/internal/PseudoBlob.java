package internal;

import filesystem.FileSystem;
import filesystem.FileUtils;

import java.io.File;
import java.nio.file.Path;

public class PseudoBlob {

    public static void generatePseudoBlobFile(FileSystem fileSystem, Path targetDir, File initialFile) {
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
