import java.util.ArrayList;

/**
 * Class to represent the user interface
 */
public class Ui {

    private String name = "Duke";
    private static final String LINE = "____________________________________________________________";
    private static final String INDENT = "    ";

    Ui() {
        say("Hi! I'm " + name);
        say("How may I help you?");
    }

    Ui(String name) {
        this.name = name;
        say("Hi! I'm " + name);
        say("How may I help you?");
    }

    /**
     * Prints text from chat bot
     * @param message Text to be said
     */
    protected void say(String message) {
        printIndented(name + ": " + message);
    }

    /**
     * Adds task to list and prints message to indicate success
     * @param message Information about added task
     * @param taskCount Number of tasks in list - 1
     */
    protected void printTaskAddedMessage(String message, int taskCount) {
        say("Got it. I've added this task");
        printBetweenBars(message);
        say("Now you have " + (taskCount + 1) + " tasks in the list");
    }

    /**
     * Prints message between two horizontal bars
     * @param message Message to be printed
     */
    protected void printBetweenBars(String message) {
        printBar();
        printIndented(message);
        printBar();
    }

    /**
     * Prints a horizontal bar
     */
    protected void printBar() {
        printIndented(LINE);
    }

    /**
     * Prints the message with indentation of four spaces
     * @param message Message to be printed
     */
    protected void printIndented(String message) {
        System.out.println(INDENT + message);
    }

    /**
     * Prints list of tasks
     * @param tasks List of tasks
     */
    protected void printList(ArrayList<Task> tasks) {
        say("Here you go");
        printBar();
        for (int i = 0; i < tasks.size(); i++) {
            printIndented((i + 1) + ". [" + tasks.get(i).getTaskIcon() + "]["
                    + tasks.get(i).getStatusIcon() + "] " + tasks.get(i).getDescription()
                    + (tasks.get(i).hasTime() ? ((tasks.get(i) instanceof Deadline ? " (by: " : " (at: ")
                    + tasks.get(i).getTime() + ")") : ""));
        }
        printBar();
    }

    protected void printMatchingItems(ArrayList<Task> tasks, String keyword) {
        say("Here are the matching tasks in your list");
        printBar();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().contains(keyword)) {
                printIndented((i + 1) + ". [" + tasks.get(i).getTaskIcon() + "]["
                        + tasks.get(i).getStatusIcon() + "] " + tasks.get(i).getDescription()
                        + (tasks.get(i).hasTime() ? ((tasks.get(i) instanceof Deadline ? " (by: " : " (at: ")
                        + tasks.get(i).getTime() + ")") : ""));
            }
        }
        printBar();
    }
}
