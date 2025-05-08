package utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestData {
    public static final String FILE_CONTENTS = "    public void removeDirectoryRecursively() {\n" +
            "        directoryManager.createDirectory(recDir);\n" +
            "        childRecDirs.forEach(dir -> directoryManager.createDirectory(dir));\n" +
            "        assertThat(directoryExists(recDir))\n" +
            "                .withFailMessage(formatErrMessage(ERRshouldHaveBeenCreated, removedDir))\n" +
            "                .isTrue();\n" +
            "        assertThat(allExists(childRecDirs)).isTrue();\n" +
            "        directoryManager.removeDirectory(recDir);\n" +
            "        assertThat(directoryExists(recDir))\n" +
            "                .withFailMessage(formatErrMessage(ERRshouldHaveBeenRemoved, recDir))\n" +
            "                .isFalse();\n" +
            "        assertThat(allExists(childRecDirs)).isFalse();\n" +
            "    }";
    public static final String FILESYSTEM_ROOT_DIR = "src/test/resources/fileSystem";
    public static final String RESOURCES_DIR = "src/test/resources";
    public static final Path FILE_WITH_CONTENTS = Path.of(FILESYSTEM_ROOT_DIR + "/fileWithContents");
    public static final Path NONEXISTENT_FILE = Path.of(FILESYSTEM_ROOT_DIR + "/nonexistentFile");

    public static final Path CREATED_DIR = Path.of(RESOURCES_DIR + "/directoryManager");
    public static final Path REMOVED_DIR = Path.of(RESOURCES_DIR + "/directoryToRemove");
    public static final Path DIR_WITH_CHILDS = Path.of(RESOURCES_DIR + "/directoryRec");
    public static final Path DUPLICATE_DIR = Path.of(RESOURCES_DIR + "/duplicate");
    public static final Path DEEP_NESTING_LEVEL_DIR = Path.of(RESOURCES_DIR + "/levelOne/LevelTwo/LevelThree/LeverFour");
    public static final Path IDEMPOTENT_DUPLICATE_DIR = Path.of(RESOURCES_DIR + "/idempotentDuplicate");
    public static final String ADDITIONAL_METHOD = "    public void removeDirectoryRecursively() {\n" +
            "        directoryManager.createDirectory(recDir);\n" +
            "        childRecDirs.forEach(dir -> directoryManager.createDirectory(dir));\n" +
            "        assertThat(directoryExists(recDir))\n" +
            "                .withFailMessage(formatErrMessage(ERRshouldHaveBeenCreated, removedDir))\n" +
            "                .isTrue();\n" +
            "        assertThat(allExists(childRecDirs)).isTrue();\n" +
            "        directoryManager.removeDirectory(recDir);\n" +
            "        assertThat(directoryExists(recDir))\n" +
            "                .withFailMessage(formatErrMessage(ERRshouldHaveBeenRemoved, recDir))\n" +
            "                .isFalse();\n" +
            "        assertThat(allExists(childRecDirs)).isFalse();\n" +
            "    }";
    public static final String FILE_EDITOR_DIRECTORY = "src/test/resources/fileEditor";
    public static final Path FILE_ONE = Path.of(FILE_EDITOR_DIRECTORY + "/one");
    public static final Path FILE_TWO = Path.of(FILE_EDITOR_DIRECTORY + "/two");
    public static final String MAIN_METHOD_UNFORMATTED = "public class Main {" +
            "\npublic static void main(String[] args) {" +
            "\n}" +
            "\n}";
    public static final String MAIN_METHOD_FORMATTED = "public class Main {" +
            "\n    public static void main(String[] args) {" +
            "\n    }" +
            "\n}";
    public static final String CREATED_FILES_FOLDER = RESOURCES_DIR + "/createdFiles";
    public static final Path NEW_TEST_FILE = Paths.get(CREATED_FILES_FOLDER + "/newTestFile");
    public static final Path DUPLICATE_FILE = Paths.get(CREATED_FILES_FOLDER + "/duplicateFile");
    public static final String NON_EXISTENT_DIRECTORY = RESOURCES_DIR + "/createdFiles/nonExistentDirectory";
    public static final String DELETED_FILES = RESOURCES_DIR + "/deletedFiles";
}
