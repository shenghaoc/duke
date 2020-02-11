import java.util.ArrayList;

/**
 * Class to represent the user interface
 */
public class Ui {

    private String name;
    private static final String LINE = "______________________________";
    private static final String INDENT = "    ";

    Ui(String name) {
        this.name = name;
        say("Hi! I'm " + name);
        say("How may I help you?");
    }

    /**
     * Prints text from chat bot
     * @param message Text to be said
     */
    protected String say(String message) {
        return indent(name + ": " + message) + System.lineSeparator();
    }

    /**
     * Adds task to list and prints message to indicate success
     * @param message Information about added task
     * @param taskCount Number of tasks in list - 1
     */
    protected String taskAddedMessage(String message, int taskCount) {
        return say("Got it. I've added this task") + format(message)
                + say("Now you have " + (taskCount + 1) + " tasks in the list");
    }

    /**
     * Prints message between two horizontal bars
     * @param message Message to be printed
     */
    protected String format(String message) {
        return bar() + indent(message) +System.lineSeparator()+ bar() + System.lineSeparator();
    }

    /**
     * Prints a horizontal bar
     */
    protected String bar() {
        return indent(LINE) + System.lineSeparator();
    }

    /**
     * Prints the message with indentation of four spaces
     * @param message Message to be printed
     */
    protected String indent(String message) {
        return INDENT + message;
    }

    /**
     * Prints list of tasks
     * @param tasks List of tasks
     */
    protected String list(ArrayList<Task> tasks) {
        StringBuilder message = new StringBuilder(say("Here you go"));
        message.append(bar());
        for (int i = 0; i < tasks.size(); i++) {
            message.append(indent((i + 1) + ". " + tasks.get(i).toString())).append(System.lineSeparator());
        }
        return message.append(bar()).toString();
    }

    protected String matchingItems(ArrayList<Task> tasks, String keyword) {
        StringBuilder message = new StringBuilder(say("Here are the matching tasks in your list"));
        message.append(bar());
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().contains(keyword)) {
                message.append(indent((i + 1) + ". " + tasks.get(i).toString())).append(System.lineSeparator());
            }
        }
        return message.append(bar()).toString();
    }
}
