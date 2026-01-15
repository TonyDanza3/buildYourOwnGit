package command;

import command.commands.Commands;
import command.commands.add.Add;
import command.commands.commit.Commit;
import command.commands.init.Init;

public class CommandFactory {

    public static Command createCommand(Commands command) {
        switch (command) {
            case INIT -> {
                return new Init();
            }
            case ADD -> {
                return new Add();
            }
            default -> {
                return new Commit();
            }
        }

    }
}
