import command.Command;
import command.CommandFactory;
import command.commands.Commands;
import command.validator.CommandValidator;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        CommandValidator commandValidator = new CommandValidator();
        if (commandValidator.isValid(args[0])) {
            Command command = CommandFactory.createCommand(Commands.getByCommandName(args[0]));
            command.execute(Arrays.copyOfRange(args, 1, args.length));
        }
    }
/* TODO:
    next command to implement is git stage

    STATUS command and possible cases
        not a repo
        does not have changed files (nothing to commit)
        has changed unstaged files
        has changed staged files

    INIT command
        (+)init new repo
        reinitialize repo
  */
}