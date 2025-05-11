import command.Command;
import command.CommandFactory;
import command.commands.Commands;
import command.validator.CommandValidator;

public class Main {

    public static void main(String[] args) {
        CommandValidator commandValidator = new CommandValidator();
        if (commandValidator.isValid(args[0])) {
            Command command = CommandFactory.createCommand(Commands.getByCommandName(args[0]));
            command.execute();
        }
    }
// TODO: is it a good idea to add generateOutput() method to Command class?
}