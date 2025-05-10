package command.validator;

import command.commands.Commands;

import java.util.Arrays;
import java.util.function.Consumer;

import static constant.ErrorMessage.INVALID_COMMAND;

public class CommandValidator {

    Consumer<String> output;
    public CommandValidator(Consumer<String> output) {
        this.output = output;
    }

    public void validate(String command) {
        //TODO: make shorter, without boolean variable ?
        boolean valid = Arrays.stream(Commands.values())
                .anyMatch(c -> c.getCommandName().equals(command));
        if(!valid) {
            output.accept(INVALID_COMMAND.formatted(command));
        }
    }
}
