package internal.index;

import filesystem.FileSystem;

import java.io.File;
import java.nio.file.Path;

import static constant.Names.INDEX_FILE_SUBDIR;
import static constant.Names.OBJECTS_FOLDER_SUB_DIR;

public class Index {
    //add
    //checkIfInIndex
    private final FileSystem fileSystem;
    private final File indexFile;
    private final Path objectsFolder;


    public Index(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        indexFile = new File((fileSystem.getGetCurrentDirectory().get() + "/" + INDEX_FILE_SUBDIR));
        objectsFolder = Path.of(fileSystem.getGetCurrentDirectory().get() + "/" + OBJECTS_FOLDER_SUB_DIR);
    }

    public void addToIndex() {

    }
}
