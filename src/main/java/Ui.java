import java.util.ArrayList;

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

    protected void say(String message) {
        printIndented(name + ": " + message);
    }

    protected void printTaskAddedMessage(String message, int taskCount) {
        say("Got it. I've added this task");
        printBetweenBars(message);
        say("Now you have " + (taskCount + 1) + " tasks in the list");
    }

    protected void printBetweenBars(String message) {
        printBar();
        printIndented(message);
        printBar();
    }

    protected void printBar() {
        printIndented(LINE);
    }

    protected void printIndented(String message) {
        System.out.println(INDENT + message);
    }

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
