package command.validator;

import command.commands.Commands;

import java.util.Arrays;
import java.util.function.Consumer;

import static command.error.ErrorMessage.INVALID_COMMAND;

public class CommandValidator {

    Consumer<String> output;
    public CommandValidator(Consumer<String> output) {
        this.output = output;
    }

    public void validate(String command) {
        boolean valid = Arrays.stream(Commands.values())
                .anyMatch(c -> c.getCommandName().equals(command));
        if(!valid) {
            output.accept(INVALID_COMMAND.formatted(command));
        }
    }
}
