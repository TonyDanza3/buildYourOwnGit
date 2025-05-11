package command;

import command.commands.Commands;
import command.commands.commit.Commit;
import command.commands.init.Init;

public class CommandFactory {

    public static Command createCommand(Commands command) {
        switch (command) {
            case INIT -> {
                return new Init();
            }
            default -> {
                return new Commit();
            }
        }

    }
}
