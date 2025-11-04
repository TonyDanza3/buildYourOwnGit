package internal.index;

import filesystem.FileSystem;
import internal.Hash;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static constant.Names.*;
import static filesystem.FileUtils.checkDirectoryExists;
import static internal.Hash.*;
import static internal.PseudoBlob.generateAndPlacePseudoBlobFile;

public class IndexOperations {

    private final FileSystem fileSystem;
    private final File indexFile;
    private final Path objectsFolder;
    private final Path currentDirectory;

    public IndexOperations(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        indexFile = new File((fileSystem.getCurrentDirectorySupplier().get() + "/" + INDEX_FILE_SUBDIR));
        objectsFolder = Path.of(fileSystem.getCurrentDirectorySupplier().get() + "/" + OBJECTS_FOLDER_SUB_DIR);
        currentDirectory = fileSystem.getCurrentDirectorySupplier().get();

    }

    //TODO: this is broken implementation, need to debug and fix this. Also it needs refac: duplicating logic must be removed
    public void addToIndex(String fileName) {
        String absoluteFileDir = currentDirectory + "/" + fileName;
        if (fileSystem.fileOnSuchPathExists(Path.of(absoluteFileDir))) {
            if (fileExistsInIndexFile(fileName)) {
                if (fileExistsInObjectsFolder(getHashFromFile(new File(absoluteFileDir)))) {
                    return;
                } else {
                    addFileBlobToObjectsFolder(fileName);
                    replaceFileRecordInIndexFile(fileName, getHashFromFile(new File(absoluteFileDir)));
                }
            } else {
                addFileBlobToObjectsFolder(fileName);
                replaceFileRecordInIndexFile(fileName, getHashFromFile(new File(absoluteFileDir)));
            }

        } else {
            return;
        }
    }


    public boolean fileExistsInIndexFile(String fileName) {
        try {
            return indexFileAsList().stream()
                    .anyMatch(
                            record ->
                                    record.getFileName().equals(fileName) &&
                                    record.getObjectSHA().equals(Hash.getHashFromFile(new File(fileSystem.currentDirectory + "/" + fileName)))
                    );
        } catch (IOException e) {
            return false;
        }
    }

    public boolean fileExistsInObjectsFolder(String hash) {
        return checkDirectoryExists(Path.of(OBJECTS_FOLDER_SUB_DIR + "/" + getFirstTwoSymbolsFromHash(hash)))
                && fileSystem.fileOnSuchPathExists(
                Path.of(fileSystem.currentDirectory + "/" +
                        OBJECTS_FOLDER_SUB_DIR +
                                "/" + getFirstTwoSymbolsFromHash(hash) +
                                "/" + getOther38SymbolsFromHash(hash)
                ));
    }

    private void addFileBlobToObjectsFolder(String fileName) throws IllegalArgumentException {
        if (fileExistsInObjectsFolder(fileName)) {
            throw new IllegalArgumentException("Could not add file " + fileName + " in objects folder because it is already there");
        }

        File fileToAddInIndex = new File(fileSystem.currentDirectory + "/" + fileName);
        String hash = getHashFromFile(fileToAddInIndex);
        String firstTwoLettersOfHash = getFirstTwoSymbolsFromHash(hash);
        Path pathOfNewPseudoBlob = Path.of(fileSystem.currentDirectory + "/" + OBJECTS_FOLDER_SUB_DIR + "/" + firstTwoLettersOfHash + "/" + getOther38SymbolsFromHash(hash));
        fileSystem.createDirectory(Path.of(fileSystem.currentDirectory + "/" + OBJECTS_FOLDER_SUB_DIR + "/" + firstTwoLettersOfHash));
        generateAndPlacePseudoBlobFile(fileSystem, pathOfNewPseudoBlob, new File(fileSystem.getCurrentDirectorySupplier().get().toString() + "/" + fileName));
    }

    private void replaceFileRecordInIndexFile(String fileName, String fileHash) {
        int lineNumberInIndexFile = getLineNumberInIndexFileByFileName(fileName);
        String indexFileRecord = Mode.USUAL_FILE.getNumberOfMOde()
                + " "
                + fileHash
                + " "
                + StagePhase.NO_CONFLICT.getPhaseNumber()
                + " "
                + fileName;
        fileSystem.replaceLineInFile(Path.of(fileSystem.currentDirectory + "/" + INDEX_FILE_SUBDIR), lineNumberInIndexFile, indexFileRecord);
    }

    private int getLineNumberInIndexFileByFileName(String fileName) {
        List<IndexRecord> allIndexRecords = new ArrayList<>();
        try {
            allIndexRecords = indexFileAsList().stream().toList();
        } catch (IOException e) {
            return 1;
        }

        for (IndexRecord record : allIndexRecords) {
            if (record.getFileName().equals(fileName)) {
                return allIndexRecords.indexOf(record);
            }
        }
        throw new RuntimeException("Unable to find file with name " + fileName + " in index file");
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
        Files.readAllLines(indexFile.toPath()).forEach(string -> {
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