package duke;

import duke.exception.DukeException;

/**
 * The main entry point for the chat bot containing the main method
 */
public class Duke {

    private static final String FILE_PATH = "data/duke.txt";
    Ui naruto = new Ui("Naruto");
    Storage storage = new Storage(FILE_PATH);
    TaskList taskList = new TaskList();
    Parser parser = new Parser();

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    public String getResponse(String input) {
        try {
            int taskNumber;
            StringBuilder message;
            switch (parser.parse(input)) {
            case BYE:
                return naruto.say("See you later!");
            case LIST:
                return naruto.list(taskList.getUpdatedTasks());
            case DONE:
                taskNumber = Integer.parseInt(input.substring("done".length() + 1)) - 1;
                taskList.addDone(taskNumber);

                // Ensure that status icon is now a tick
                assert taskList.getStatusIcon(taskNumber).equals("\u2713") : "duke.task.Task status not done!";

                message = new StringBuilder(naruto.say("All right, consider it done"));
                message.append(naruto.format("[" + taskList.getStatusIcon(taskNumber) + "] "
                        + taskList.getDescription(taskNumber)));
                storage.save(taskList.getUpdatedTasks());
                return message.toString();
            case TODO:
                try {
                    taskNumber = taskList.addToDo(input);
                    message = new StringBuilder(naruto.taskAddedMessage(taskList.getTask(taskNumber).toString(), taskNumber));
                    storage.save(taskList.getUpdatedTasks());
                    return message.toString();
                } catch (DukeException dE) {
                    return naruto.say("☹ OOPS!!! The description of a todo cannot be empty.");
                }
            case DEADLINE:
                taskNumber = taskList.addDeadline(input);
                message = new StringBuilder(naruto.taskAddedMessage(taskList.getTask(taskNumber).toString(), taskNumber));
                storage.save(taskList.getUpdatedTasks());
                return message.toString();
            case EVENT:
                taskNumber = taskList.addEvent(input);
                message = new StringBuilder(naruto.taskAddedMessage(taskList.getTask(taskNumber).toString(), taskNumber));
                storage.save(taskList.getUpdatedTasks());
                return message.toString();
            case DELETE:
                int originalTaskCount = taskList.getTaskCount();
                taskNumber = Integer.parseInt(input.substring("delete".length() + 1)) - 1;
                message = new StringBuilder(naruto.say("Noted. I've removed this duke.task"));
                message.append(naruto.format("[" + taskList.getTaskIcon(taskNumber) + "]["
                        + taskList.getStatusIcon(taskNumber) + "] " + taskList.getDescription(taskNumber)));
                taskList.delete(taskNumber);
                message.append(naruto.say("Now you have " + taskList.getTaskCount() + " tasks in the list"));
                // Ensure number of tasks falls by 1
                assert taskList.getTaskCount() == originalTaskCount - 1 : "Number of events unchanged!";
                storage.save(taskList.getUpdatedTasks());
                return message.toString();
            case FIND:
                return naruto.matchingItems(taskList.getUpdatedTasks(), input.substring("find".length() + 1));
            }
        } catch (DukeException dE) {
            return naruto.say("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        return "duke.Duke heard: " + input;
    }
}
