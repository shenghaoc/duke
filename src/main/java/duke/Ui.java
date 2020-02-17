package duke;

import duke.task.Task;

import java.util.ArrayList;

/**
 * Class to represent the user interface.
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
     * Prints text from chat bot.
     * @param message Text to be said
     */
    protected String say(String message) {
        return indent(name + ": " + message) + System.lineSeparator();
    }

    /**
     * Adds duke.task to list and prints message to indicate success.
     * @param message Information about added duke.task
     * @param taskNumber Number of tasks in list - 1
     */
    protected String taskAddedMessage(String message, int taskNumber) {
        return say("Got it. I've added this duke.task") + format(message)
                + say("Now you have " + (taskNumber + 1) + " tasks in the list");
    }

    /**
     * Prints message between two horizontal bars.
     * @param message Message to be printed
     */
    protected String format(String message) {
        return bar() + indent(message) + System.lineSeparator() + bar() + System.lineSeparator();
    }

    /**
     * Prints a horizontal bar.
     */
    protected String bar() {
        return indent(LINE) + System.lineSeparator();
    }

    /**
     * Prints the message with indentation of four spaces.
     * @param message Message to be printed
     */
    protected String indent(String message) {
        return INDENT + message;
    }

    /**
     * Prints list of tasks.
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
        int defaultLength = message.length();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().contains(keyword)) {
                message.append(indent((i + 1) + ". " + tasks.get(i).toString())).append(System.lineSeparator());
            }
        }
        if (message.length() == defaultLength) {
            return partialMatchingItems(tasks, keyword);
        }
        return message.append(bar()).toString();
    }

    protected String partialMatchingItems(ArrayList<Task> tasks, String keyword) {
        StringBuilder message = new StringBuilder(
                say("No matching task found, here are the partially-matching tasks in your list"));
        message.append(bar());
        String[] subkeywords = keyword.split(" ");
        for (String s : subkeywords) {
            message.append(indent("Match \"" + s + "\"")).append(System.lineSeparator());
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getDescription().contains(s)) {
                    message.append(indent((i + 1) + ". " + tasks.get(i).toString())).append(System.lineSeparator());
                }
            }
            message.append(System.lineSeparator());
        }
        return message.append(bar()).toString();
    }
}
