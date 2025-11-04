package internal;

import filesystem.FileSystem;
import internal.index.IndexOperations;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static command.commands.init.GitFolders.OBJECTS_INFO;
import static command.commands.init.GitFolders.OBJECTS_PACK;
import static constant.Names.GIT_FOLDER_NAME;
import static constant.Names.OBJECTS_FOLDER_SUB_DIR;
import static filesystem.utils.FileSystemTestUtils.fileToString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Assertion {


    public static void fileAddedToIndexFile(FileSystem fileSystem, String fileName) {
        IndexOperations indexOperations = new IndexOperations(fileSystem);
        assertThat(indexOperations.fileExistsInIndexFile(fileName))
                .withFailMessage("File " + fileName + " was not added to index file. Current index file contents is: \n \n" + getIndexFileContents(fileSystem) + "\n")
                .isTrue();
    }

    public static void fileAddedToIndexFolder(FileSystem fileSystem, Path filePath) {
        IndexOperations indexOperations = new IndexOperations(fileSystem);
        String hash = Hash.getHashFromFile(new File(String.valueOf(fileSystem.currentDirectory + "/" + filePath)));
        assertThat(indexOperations.fileExistsInObjectsFolder(hash))
                .withFailMessage("File was not added to objects folder. Hash of file is " + hash + " but " +
                        "the objects folder contains only this subfolders (which probably does not contain) first " +
                        "letters of this hash  '" + hash + "' : \n" + getListOfObjectsSubdoldersAsString(fileSystem))
                .isTrue();
    }

    public static void checkIndexFileEmpty(FileSystem fileSystem) {
        Path objectsDir = Path.of(fileSystem.currentDirectory + "/" + OBJECTS_FOLDER_SUB_DIR);
        Path gitSubdir = Path.of(fileSystem.currentDirectory + "/" + GIT_FOLDER_NAME);
        List<Path> objectsSubfolders = fileSystem.getSubfoldersFromDirectory(objectsDir, Path.of(gitSubdir
                + "/" + OBJECTS_INFO.getFolderName()), Path.of(gitSubdir + "/" + OBJECTS_PACK.getFolderName()));
        assertThat(objectsSubfolders.isEmpty())
                .withFailMessage(objectsDir + " is not empty. It contains the following dirs:\n" + objectsSubfolders)
                .isTrue();
    }

    private static String getListOfObjectsSubdoldersAsString(FileSystem fileSystem) {
        StringBuilder builder = new StringBuilder();
        List<Path> paths = fileSystem.getSubfoldersFromDirectory(Path.of(fileSystem.currentDirectory + "/" + OBJECTS_FOLDER_SUB_DIR));
        paths.forEach(p -> {
            builder.append(p);
            builder.append("\n");
        });
        return builder.toString();
    }

    private static String getIndexFileContents(FileSystem fileSystem) {
        return fileToString(Path.of(fileSystem.currentDirectory + "/" +GIT_FOLDER_NAME + "/index"));
    }
}
