package command;

import command.validator.CommandValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class CommandValidatorTest {

    @Test
    public void validCommand() {
        List<String> outputs = new ArrayList<>();
        CommandValidator commandValidator = new CommandValidator(outputs::add);
        commandValidator.validate("init");
        assertThat(outputs.isEmpty())
                .withFailMessage("'init' command should not have triggered error, but it did")
                .isTrue();
    }

    @Test
    public void invalidCommand() {
        List<String> outputs = new ArrayList<>();
        CommandValidator commandValidator = new CommandValidator(outputs::add);
        commandValidator.validate("initg");
        assertThat(outputs.get(0).equals("git: 'initg' is not a git command. See 'git --help'."))
                .withFailMessage("'initg' command should have trigger error, but it did not")
                .isTrue();
    }
}
