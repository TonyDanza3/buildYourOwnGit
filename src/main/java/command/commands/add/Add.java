package command.commands.add;

import command.Command;
import command.commands.Commands;
import internal.index.IndexOperations;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Add extends Command {

    IndexOperations indexOPerations;

    public Add() {
        super(Commands.ADD);
        indexOPerations = new IndexOperations(fileSystem);
    }

    public Add(Supplier<Path> getCurrentDirectory, Consumer<String> routeCommandOutput) {
        super(Commands.ADD, getCurrentDirectory, routeCommandOutput);
        indexOPerations = new IndexOperations(fileSystem);
    }

    @Override
    public boolean validateArgs(String[] args) {
        for (String arg : args) {
            if (!fileSystem.fileOnSuchPathExists(Path.of(fileSystem.getCurrentDirectorySupplier().get() + "/" +arg))) {
                routeCommandOutput.accept("fatal: pathspec '" + arg + "' did not match any files");
                return false;
            }
        }
        return true;
    }

    @Override
    public void executeCommand(String[] args) {
        Arrays.asList(args).forEach(argg -> {
            indexOPerations.addToIndex(argg);
        });
    }
}

