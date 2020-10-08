package seedu;

import seedu.command.ExitCommand;
import seedu.command.ListCommand;
import seedu.command.FindCommand;
import seedu.command.DoneCommand;
import seedu.command.AddCommand;
import seedu.command.DeleteCommand;
import seedu.command.Command;
import seedu.task.Deadline;
import seedu.task.ToDo;
import seedu.task.Event;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *Parser Object is used for translating String user input into
 * a actionable Command object for execution
 */
public class Parser {
    public Parser(){}
    /**
     * Convert the given string input into a subclass of Command class.
     * return different subclass of Command class
     *
     * @param input User input
     * @return a subclass of Command class
     * @throws DukeException if invalid input
     */
    public static Command parse(String input) throws DukeException {
        int taskNum;
        String[] words = input.split(" ");
            switch (words[0].toLowerCase()){
            case "bye":
                //Fallthrough
                return new ExitCommand();
            case "list":
                //Fallthrough
                return new ListCommand();
            case "done":
                taskNum = Integer.parseInt(words[1]);
                //Fallthrough
                return  new DoneCommand(taskNum-1);
            case "delete":
                taskNum = Integer.parseInt(words[1]);
                //Fallthrough
                return new DeleteCommand(taskNum-1);
            case "find":
                String[] sentence = input.toLowerCase().split(" ",2);
                String keywords=sentence[1];
                //Fallthrough
                return new FindCommand(keywords);
            case "todo":
                ToDo todo = validateToDo(input);
                //Fallthrough
                return new AddCommand(todo);
            case "deadline":
                Deadline deadline = validateDeadline(input);
                //Fallthrough
                return new AddCommand(deadline);
            case "event":
                Event ev = validateEvent(input);
                //Fallthrough
                return new AddCommand(ev);
            default:
                throw new DukeException(DukeExceptionType.INVALID_COMMAND);
            }
    }

    /**
     * Used to validate and check for any errors in the user input
     * for ToDo object
     *
     * @param  input representing user input
     * @return Todo object
     * @throws DukeException if missing information
     */
    public static ToDo validateToDo(String input) throws DukeException {
        ToDo t;
        String[] filteredInput = input.trim().split(" ",2);

        if (filteredInput.length == 1) {
            throw new DukeException(DukeExceptionType.MISSING_DESCRIPTION);
        } else {
            t = new ToDo(filteredInput[1]);
        }
        return t;
    }

    /**
     * Used to validate and check for any errors in the user input
     * for DeadLine object
     *
     * @param  input representing user input
     * @return DeadLine object
     * @throws DukeException if missing information
     */
    public static Deadline validateDeadline(String input) throws DukeException {
        Deadline e;
        String[] filteredInput = input.trim().split(" ",2);

        if (filteredInput.length == 1) {
            throw new DukeException(DukeExceptionType.MISSING_DESCRIPTION);
        }  else if (!filteredInput[1].contains("/by")) {
            throw new DukeException(DukeExceptionType.MISSING_DEADLINE);
        } else {
            String[] descriptByFilter = filteredInput[1].split("/by",2);
            String byInfo = parseForDate(descriptByFilter[1]);
            e = new Deadline(descriptByFilter[0],byInfo);
        }
        return e;
    }

    /**
     * Used to validate and check for any errors in the user input
     * for Event object
     *
     * @param  input representing user input
     * @return Event object
     * @throws DukeException if missing information
     */
    public static Event validateEvent(String input) throws DukeException {
        Event e;
        String[] filteredInput = input.trim().split(" ",2);

        if (filteredInput.length == 1) {
            throw new DukeException(DukeExceptionType.MISSING_DESCRIPTION);
        }  else if (!filteredInput[1].contains("/at")) {
            throw new DukeException(DukeExceptionType.MISSING_EVENT_INFO);
        } else {
            String[] descriptAtFilter = filteredInput[1].split("/at",2);
            String atInfo = parseForDate(descriptAtFilter[1]);
            e = new Event(descriptAtFilter[0],atInfo);
        }
        return e;
    }

    public static String parseForDate(String input)  throws DukeException{
    try {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("d-MM-yyyy");
        LocalDate dateFormatted = LocalDate.parse(input.trim(), df);
        return dateFormatted.format(DateTimeFormatter.ofPattern("d MMM yyyy"));
    } catch (DateTimeException e) {
        throw new DukeException(DukeExceptionType.WRONG_DATE_FORMAT);
    }

    }
}
