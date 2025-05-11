import command.Command;
import command.CommandFactory;
import command.commands.Commands;
import command.validator.CommandValidator;
import filesystem.FileUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        CommandValidator commandValidator = new CommandValidator();
        if (commandValidator.validate(args[0])) {
            Command command = CommandFactory.createCommand(Commands.valueOf(args[0]));
            command.execute();
        }
    }
}