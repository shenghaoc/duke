import java.util.Scanner;

public class Duke {

    private static final String name = "Naruto";
    private static final Scanner sc = new Scanner(System.in);
    private static final String line = "____________________________________________________________";
    private static final String indent = "    ";

    public static void main(String[] args) {
        System.out.println("Hi! I'm " + name);
        System.out.println("How may I help you?");

        Task[] tasks = new Task[100];
        int taskCount = 0;
        for (; ; ) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                printIndented(name + ": See you later!");
                break;
            } else if (input.equals("list")) {
                printList(tasks, taskCount);
            } else if (input.startsWith("done")) {
                addDone(Integer.parseInt(input.substring("done".length() + 1)) - 1, tasks);
            } else if (input.startsWith("todo")) {
                addTodo(input, tasks, taskCount);
                taskCount++;
            } else if (input.startsWith("deadline")) {
                addDeadline(input, tasks, taskCount);
                taskCount++;
            } else if (input.startsWith("event")) {
                addEvent(input, tasks, taskCount);
                taskCount++;
            } else {
                System.out.println(" ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private static void printList(Task[] tasks, int taskCount) {
        printIndented(name + ": Here you go");
        printIndented(line);
        for (int i = 0; i < taskCount; i++) {
            printIndented((i + 1) + ". [" + tasks[i].getTaskIcon() + "]["
                    + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription()
                    + (tasks[i].hasTime() ? ((tasks[i] instanceof Deadline ? " (by: " : " (at: ")
                    + tasks[i].getTime() + ")") : ""));
        }
        printIndented(line);
    }

    private static void printIndented(String message) {
        System.out.println(indent + message);
    }

    private static void printBetweenBars(String message) {
        printIndented(line);
        System.out.println(message);
        printIndented(line);
    }

    private static void printTaskAddedMessage(String message, int taskCount) {
        printIndented(name + ": Got it. I've added this task");
        printBetweenBars(message);
        printIndented(name + ": Now you have " + (taskCount + 1) + " tasks in the list");
    }

    private static void addTodo(String input, Task[] tasks, int taskCount) {
        tasks[taskCount] = new Todo(input.substring("todo".length() + 1));
        printTaskAddedMessage(indent + "[" + tasks[taskCount].getTaskIcon() + "]["
                + tasks[taskCount].getStatusIcon() + "] " + tasks[taskCount].getDescription(), taskCount);
    }

    private static void addDeadline(String input, Task[] tasks, int taskCount) {
        int trigger = input.indexOf('/');
        tasks[taskCount] = new Deadline(input.substring("deadline".length() + 1, trigger - 1),
                input.substring(trigger + "/by ".length()));

        printTaskAddedMessage(indent + "[" + tasks[taskCount].getTaskIcon() + "]["
                + tasks[taskCount].getStatusIcon() + "] " + tasks[taskCount].getDescription(), taskCount);
    }

    private static void addEvent(String input, Task[] tasks, int taskCount) {
        int trigger = input.indexOf('/');
        tasks[taskCount] = new Event(input.substring("event".length() + 1, trigger - 1),
                input.substring(trigger + "/at ".length()));

        printTaskAddedMessage(indent + "[" + tasks[taskCount].getTaskIcon() + "]["
                + tasks[taskCount].getStatusIcon() + "] " + tasks[taskCount].getDescription(), taskCount);
    }

    private static void addDone(int taskNumber, Task[] tasks) {
        tasks[taskNumber].markAsDone();
        printIndented(name + ": All right, consider it done: ");
        printBetweenBars("[" + tasks[taskNumber].getStatusIcon() + "] " + tasks[taskNumber].getDescription());
    }

}
