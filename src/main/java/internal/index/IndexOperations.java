package internal.index;

import filesystem.FileSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static constant.Names.*;
import static internal.Hash.*;
import static internal.PseudoBlob.generatePseudoBlobFile;

public class IndexOperations {

    private final FileSystem fileSystem;
    private final File indexFile;
    private final Path objectsFolder;

    public IndexOperations(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        indexFile = new File((fileSystem.getCurrentDirectorySupplier().get() + "/" + INDEX_FILE_SUBDIR));
        objectsFolder = Path.of(fileSystem.getCurrentDirectorySupplier().get() + "/" + OBJECTS_FOLDER_SUB_DIR);

    }

    public void addToIndex(String fileName) {
        if (fileSystem.fileOnSuchPathExists(Path.of(fileName))) {
            if (fileExistsInIndexFile(fileName)) {
                if (fileExistsInObjectsFolder(getHashFromFile(new File(fileName)))) {
                    return;
                } else {

                }
            } else {
            }

        } else {

        }
    }

    private void addFileToObjectsFolder(String fileName) throws IllegalArgumentException {
        if (fileExistsInObjectsFolder(fileName)) {
            throw new IllegalArgumentException("Could not add file " + fileName + " in objects folder because it is already there");
        }

        File fileToAddInIndex = new File(fileName);
        String hash = getHashFromFile(fileToAddInIndex);
        String firstTwoLettersOfHash = getFirstTwoSymbolsFromHash(hash);
        Path pathOfNewPseudoBlob = Path.of(fileSystem.currentDirectory + "/" + OBJECTS_FOLDER_SUB_DIR + "/" + firstTwoLettersOfHash + "/" + getOther38SymbolsFromHash(hash));
        fileSystem.createDirectory(Path.of(fileSystem.currentDirectory + "/" + OBJECTS_FOLDER_SUB_DIR + "/" + firstTwoLettersOfHash));
        generatePseudoBlobFile(fileSystem, pathOfNewPseudoBlob, new File(fileName));
        addFileRecordToIndexFile(fileName, hash);
    }

    private void addFileRecordToIndexFile(String fileName, String fileHash) {
        String indexFileRecord = Mode.USUAL_FILE.getNumberOfMOde()
                + " "
                + fileHash
                + " "
                + StagePhase.NO_CONFLICT.getPhaseNumber()
                + "\t"
                + fileName;
        fileSystem.appendLineToFile(Path.of(INDEX_FILE_SUBDIR), indexFileRecord);
    }

    private boolean fileExistsInIndexFile(String fileName) {
        try {
            return indexFileAsList().stream()
                    .anyMatch(record -> record.getFileName().equals(fileName));
        } catch (IOException e) {
            return false;
        }
    }

    private boolean fileExistsInObjectsFolder(String hash) {
        return true;
    }

    private IndexRecord getFileRecordFromIndexFile(String fileName) throws IOException {
        IndexRecord indexRecordOfFile = null;
        try {
            indexRecordOfFile = indexFileAsList().stream()
                    .filter(record -> record.getFileName().equals(fileName))
                    .toList()
                    .get(0);
        } catch (IOException ignored) {
        }
        if (indexRecordOfFile == null) {
            throw new IOException("There is no file " + fileName + " in index");
        }
        return indexRecordOfFile;
    }

    private List<IndexRecord> indexFileAsList() throws IOException {
        List<IndexRecord> allIndexRecords = new ArrayList<>();
        Arrays.asList(indexFile.list()).forEach(string -> {
            String[] tokenizedString = string.split(" ");
            allIndexRecords.add(IndexRecord.builder()
                    .mode(Mode.getMode(Integer.parseInt(tokenizedString[0])))
                    .objectSHA(tokenizedString[1])
                    .stagePhase(StagePhase.getPhaseByNumber(Integer.parseInt(tokenizedString[2])))
                    .fileName(tokenizedString[3])
                    .build());
        });
        if (allIndexRecords.isEmpty()) {
            throw new IOException("Index file is empty");
        }
        return allIndexRecords;
    }
}