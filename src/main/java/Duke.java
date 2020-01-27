import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Duke {

    private static final String NAME = "Naruto";
    private static final String LINE = "____________________________________________________________";
    private static final String INDENT = "    ";
    private static final String FILE_PATH = "data/duke.txt";

    public static void main(String[] args) {
        File f = new File(FILE_PATH);
        Scanner sc = new Scanner(System.in);
        say("Hi! I'm " + NAME);
        say("How may I help you?");

        ArrayList<Task> tasks = new ArrayList<>();
        int taskCount = 0;
        for (; ; ) {
            String input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    say("See you later!");
                    break;
                } else if (input.equals("list")) {
                    printList(tasks, taskCount);
                } else if (input.startsWith("done")) {
                    addDone(Integer.parseInt(input.substring("done".length() + 1)) - 1, tasks);
                    save(tasks, f);
                } else if (input.startsWith("todo")) {
                    try {
                        addToDo(input, tasks, taskCount);
                        taskCount++;
                        save(tasks, f);
                    } catch (DukeException dE) {
                        say("☹ OOPS!!! The description of a todo cannot be empty.");
                    }
                } else if (input.startsWith("deadline")) {
                    addDeadLINE(input, tasks, taskCount);
                    taskCount++;
                    save(tasks, f);
                } else if (input.startsWith("event")) {
                    addEvent(input, tasks, taskCount);
                    taskCount++;
                    save(tasks, f);
                } else if (input.startsWith("delete")) {
                    taskCount--;
                    delete(Integer.parseInt(input.substring("delete".length() + 1)) - 1, tasks, taskCount);
                    save(tasks, f);
                } else {
                    throw new DukeException();
                }
            } catch (DukeException dE) {
                say("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    private static void say(String message) {
        printIndented(NAME + ": " + message);
    }

    private static void printList(ArrayList<Task> tasks, int taskCount) {
        say("Here you go");
        printIndented(LINE);
        for (int i = 0; i < taskCount; i++) {
            printIndented((i + 1) + ". [" + tasks.get(i).getTaskIcon() + "]["
                    + tasks.get(i).getStatusIcon() + "] " + tasks.get(i).getDescription()
                    + (tasks.get(i).hasTime() ? ((tasks.get(i) instanceof Deadline ? " (by: " : " (at: ")
                    + tasks.get(i).getTime() + ")") : ""));
        }
        printIndented(LINE);
    }

    private static void printIndented(String message) {
        System.out.println(INDENT + message);
    }

    private static void printBetweenBars(String message) {
        printIndented(LINE);
        printIndented(message);
        printIndented(LINE);
    }

    private static void printTaskAddedMessage(String message, int taskCount) {
        say("Got it. I've added this task");
        printBetweenBars(message);
        say("Now you have " + (taskCount + 1) + " tasks in the list");
    }

    private static void addToDo(String input, ArrayList<Task> tasks, int taskCount) throws DukeException {
        if (input.length() <= ("todo".length() + 1)) {
            throw new DukeException();
        }
        tasks.add(new ToDo(input.substring("todo".length() + 1)));
        printTaskAddedMessage("[" + tasks.get(taskCount).getTaskIcon() + "]["
                + tasks.get(taskCount).getStatusIcon() + "] " + tasks.get(taskCount).getDescription(), taskCount);
    }

    private static void addDeadLINE(String input, ArrayList<Task> tasks, int taskCount) {
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
        say("All right, consider it done");
        printBetweenBars("[" + tasks.get(taskNumber).getStatusIcon() + "] " + tasks.get(taskNumber).getDescription());
    }

    private static void delete(int taskNumber, ArrayList<Task> tasks, int taskCount) {
        say("Noted. I've removed this task");
        printBetweenBars("[" + tasks.get(taskNumber).getTaskIcon() + "]["
                + tasks.get(taskNumber).getStatusIcon() + "] " + tasks.get(taskNumber).getDescription());
        say("Now you have " + taskCount + " tasks in the list");
        tasks.remove(taskNumber);
    }

    private static void save(ArrayList<Task> tasks, File f) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < tasks.size(); i++) {
                fw.write((i + 1) + ". [" + tasks.get(i).getTaskIcon() + "]["
                        + tasks.get(i).getStatusIcon() + "] " + tasks.get(i).getDescription()
                        + (tasks.get(i).hasTime() ? ((tasks.get(i) instanceof Deadline ? " (by: " : " (at: ")
                        + tasks.get(i).getTime() + ")") : "") + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

}
