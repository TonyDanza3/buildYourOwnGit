package command.validator;

import command.commands.Commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

import static constant.ErrorMessage.INVALID_COMMAND;

public class CommandValidator {

    Consumer<String> output;

    public CommandValidator(Consumer<String> output) {
        this.output = output;
    }

    public CommandValidator() {
        this.output = System.out::println;
    }

    public boolean validate(String command) {
        boolean valid = Arrays.stream(Commands.values())
                .anyMatch(c -> c.getCommandName().equals(command));
        if (!valid) {
            output.accept(INVALID_COMMAND.formatted(command));
        }
        return valid;
    }
}