package command.commands.add;

import command.Command;
import command.commands.Commands;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Add extends Command {

    public Add() {
        super(Commands.ADD);
    }

    public Add(Supplier<Path> getCurrentDirectory, Consumer<String> routeCommandOutput) {
        super(Commands.ADD, getCurrentDirectory, routeCommandOutput);
    }

    @Override
    public boolean validateArgs(String[]args) {
        for(String arg: args) {
            if(!fileSystem.fileOnSuchPathExists(Path.of(arg))) {
                routeCommandOutput.accept("fatal: pathspec '" + arg + "' did not match any files");
                return false;
            }
        }
        return true;
    }

    @Override
    public void executeCommand(String[]args) {
        for(String arg: args) {
            //TODO: I stopped here, continue from here
        }
    }
}
