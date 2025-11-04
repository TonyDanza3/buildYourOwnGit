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
        // можно подсмотреть тут верхнеуровнево как это будет работать и что нужно заимплементить для того чтобы эта команда работала https://chatgpt.com/c/6904a5fd-5784-8330-9f2a-f7addf6c449e
        return true;
    }

    @Override
    protected void executeCommand(String[]args) {

    }
}