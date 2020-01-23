import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    private static final String name = "Naruto";
    private static final Scanner sc = new Scanner(System.in);
    private static final String line = "____________________________________________________________";
    private static final String indent = "    ";

    public static void main(String[] args) {
        System.out.println("Hi! I'm " + name);
        System.out.println("How may I help you?");

        ArrayList<Task> tasks = new ArrayList<>();
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
                try {
                    addToDo(input, tasks, taskCount);
                    taskCount++;
                } catch (DukeException dE) {
                    System.out.println("☹ OOPS!!! The description of a todo cannot be empty.");
                }
            } else if (input.startsWith("deadline")) {
                addDeadline(input, tasks, taskCount);
                taskCount++;
            } else if (input.startsWith("event")) {
                addEvent(input, tasks, taskCount);
                taskCount++;
            } else if (input.startsWith("delete")) {
                taskCount--;
                delete(Integer.parseInt(input.substring("delete".length() + 1)) - 1, tasks, taskCount);
            } else {
                System.out.println("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private static void printList(ArrayList<Task> tasks, int taskCount) {
        printIndented(name + ": Here you go");
        printIndented(line);
        for (int i = 0; i < taskCount; i++) {
            printIndented((i + 1) + ". [" + tasks.get(i).getTaskIcon() + "]["
                    + tasks.get(i).getStatusIcon() + "] " + tasks.get(i).getDescription()
                    + (tasks.get(i).hasTime() ? ((tasks.get(i) instanceof Deadline ? " (by: " : " (at: ")
                    + tasks.get(i).getTime() + ")") : ""));
        }
        printIndented(line);
    }

    private static void printIndented(String message) {
        System.out.println(indent + message);
    }

    private static void printBetweenBars(String message) {
        printIndented(line);
        printIndented(message);
        printIndented(line);
    }

    private static void printTaskAddedMessage(String message, int taskCount) {
        printIndented(name + ": Got it. I've added this task");
        printBetweenBars(message);
        printIndented(name + ": Now you have " + (taskCount + 1) + " tasks in the list");
    }

    private static void addToDo(String input, ArrayList<Task> tasks, int taskCount) throws DukeException {
        if (input.length() <= ("todo".length() + 1)) {
            throw new DukeException();
        }
        tasks.add(new ToDo(input.substring("todo".length() + 1)));
        printTaskAddedMessage("[" + tasks.get(taskCount).getTaskIcon() + "]["
                + tasks.get(taskCount).getStatusIcon() + "] " + tasks.get(taskCount).getDescription(), taskCount);
    }

    private static void addDeadline(String input, ArrayList<Task> tasks, int taskCount) {
        int trigger = input.indexOf('/');
        tasks.add(new Deadline(input.substring("deadline".length() + 1, trigger - 1),
                input.substring(trigger + "/by ".length())));

        printTaskAddedMessage("[" + tasks.get(taskCount).getTaskIcon() + "]["
                + tasks.get(taskCount).getStatusIcon() + "] " + tasks.get(taskCount).getDescription(), taskCount);
    }

    private static void addEvent(String input, ArrayList<Task> tasks, int taskCount) {
        int trigger = input.indexOf('/');
        tasks.add(new Event(input.substring("event".length() + 1, trigger - 1),
                input.substring(trigger + "/at ".length())));

        printTaskAddedMessage("[" + tasks.get(taskCount).getTaskIcon() + "]["
                + tasks.get(taskCount).getStatusIcon() + "] " + tasks.get(taskCount).getDescription(), taskCount);
    }

    private static void addDone(int taskNumber, ArrayList<Task> tasks) {
        tasks.get(taskNumber).markAsDone();
        printIndented(name + ": All right, consider it done");
        printBetweenBars("[" + tasks.get(taskNumber).getStatusIcon() + "] " + tasks.get(taskNumber).getDescription());
    }

    private static void delete(int taskNumber, ArrayList<Task> tasks, int taskCount) {
        printIndented(name + ": Noted. I've removed this task");
        printBetweenBars("[" + tasks.get(taskNumber).getTaskIcon() + "]["
                + tasks.get(taskNumber).getStatusIcon() + "] " + tasks.get(taskNumber).getDescription());
        printIndented(name + ": Now you have " + taskCount + " tasks in the list");
        tasks.remove(taskNumber);
    }

}
