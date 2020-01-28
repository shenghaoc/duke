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


}
