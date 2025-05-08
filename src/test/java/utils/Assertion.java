package utils;

import org.assertj.core.api.SoftAssertions;
import java.nio.file.Path;
import java.util.List;

import static utils.FileTestUtils.checkDirectoryExists;
import static utils.FileTestUtils.checkFileExists;

public class Assertion {

    public static void fileExists(Path ... files) {
        SoftAssertions assertions = new SoftAssertions();
        for(Path file: files) {
            assertions.assertThat(checkFileExists(file))
                    .withFailMessage("File " + file + " does not exist but it should have been created")
                    .isTrue();
        }
        assertions.assertAll();
    }

    public static void directoryExists(Path ... directories) {
        SoftAssertions assertions = new SoftAssertions();
        for(Path directory: directories) {
            assertions.assertThat(checkDirectoryExists(directory))
                    .withFailMessage("Directory " + directory + " does not exist but it should have been created")
                    .isTrue();
        }
        assertions.assertAll();
    }

    public static void directoryExists(List<Path> directories) {
        SoftAssertions assertions = new SoftAssertions();
        for(Path directory: directories) {
            assertions.assertThat(checkDirectoryExists(directory))
                    .withFailMessage("Directory " + directory + " does not exist but it should have been created")
                    .isTrue();
        }
        assertions.assertAll();
    }

    public static void directoryNotExists(Path ... directories) {
        SoftAssertions assertions = new SoftAssertions();
        for(Path directory: directories) {
            assertions.assertThat(checkDirectoryExists(directory))
                    .withFailMessage("Directory " + directory + " exists but it shouldn't")
                    .isFalse();
        }
        assertions.assertAll();
    }

    public static void directoryNotExists(List<Path> directories) {
        SoftAssertions assertions = new SoftAssertions();
        for(Path directory: directories) {
            assertions.assertThat(checkDirectoryExists(directory))
                    .withFailMessage("Directory " + directory + " exists but it shouldn't")
                    .isFalse();
        }
        assertions.assertAll();
    }

    public static void fileNotExists(Path ... files) {
        SoftAssertions assertions = new SoftAssertions();
        for(Path file: files) {
            assertions.assertThat(checkFileExists(file))
                    .withFailMessage("File " + file + " exists but it shouldn't")
                    .isFalse();
        }
        assertions.assertAll();
    }
}