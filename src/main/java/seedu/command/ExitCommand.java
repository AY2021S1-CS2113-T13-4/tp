package seedu.command;

import seedu.Storage;
import seedu.task.TaskList;
import seedu.Ui;

/**
 * Represents a Command for exiting the application
 */
public class ExitCommand extends Command {
    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {

    }
}