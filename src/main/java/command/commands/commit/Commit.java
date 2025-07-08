package command.commands.commit;

import command.Command;
import command.commands.Commands;

public class Commit extends Command {

    public Commit() {
        super(Commands.COMMIT);
    }
    @Override
    protected boolean validateArgs(String[]args) {
        //temporary
        return true;
    }

    @Override
    protected void executeCommand(String[]args) {

    }
}
