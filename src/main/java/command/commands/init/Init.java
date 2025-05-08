package command.commands.init;

import command.Command;
import command.commands.Commands;

public class Init extends Command {

    public Init() {
        super(Commands.INIT);
    }

    @Override
    public void execute() {}

    @Override
    public void validateArgs() {}
}
