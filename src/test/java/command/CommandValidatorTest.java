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
        commandValidator.isValid("init");
        assertThat(outputs.isEmpty())
                .withFailMessage("'init' command should not have triggered error, but it did")
                .isTrue();
    }

    @Test
    public void invalidCommand() {
        List<String> outputs = new ArrayList<>();
        CommandValidator commandValidator = new CommandValidator(outputs::add);
        commandValidator.isValid("initg");
        assertThat(outputs.isEmpty())
                .withFailMessage("'initg' command should have trigger error, but it did not")
                .isFalse();
        assertThat(outputs.get(0).equals("gi: 'initg' is not a gi command. See 'gi --help'."))
                .withFailMessage("'initg' command should have trigger an error message equals to: \n'gi: 'initg' is not a gi command. See 'gi --help'.' \n\n but actual error is:\n '" + outputs.get(0) + "'")
                .isTrue();
    }
}