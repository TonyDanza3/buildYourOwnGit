package command.validator;

import command.Command;
import command.commands.Commands;

import java.util.Arrays;

import static command.error.ErrorMessage.INVALID_COMMAND;

public class CommandValidator {

    public void validate(String command) {
        boolean valid = Arrays.stream(Commands.values())
                .anyMatch(c -> c.getCommandName().equals(command));
        if(!valid) {
            throw new RuntimeException(INVALID_COMMAND.formatted(command));
        }
    }
}
