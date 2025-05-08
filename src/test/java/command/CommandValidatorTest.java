package command;

import command.validator.CommandValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandValidatorTest {

    CommandValidator commandValidator = new CommandValidator();

    @Test
    public void validCommand() {
        assertDoesNotThrow(() -> commandValidator.validate("init"));
    }

    @Test
    public void invalidCommand() {
        assertThrows(RuntimeException.class, () -> commandValidator.validate("initg"));
    }
}
