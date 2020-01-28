import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    private static final String FILE_PATH = "data/duke.txt";
    private static Ui naruto;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        naruto = new Ui("Naruto");
        Storage storage = new Storage(FILE_PATH);

        ArrayList<Task> tasks = new ArrayList<>();
        int taskCount = 0;
        for (; ; ) {
            String input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    naruto.say("See you later!");
                    break;
                } else if (input.equals("list")) {
                    printList(tasks, taskCount);
                } else if (input.startsWith("done")) {
                    addDone(Integer.parseInt(input.substring("done".length() + 1)) - 1, tasks);
                    storage.save(tasks);
                } else if (input.startsWith("todo")) {
                    try {
                        addToDo(input, tasks, taskCount);
                        taskCount++;
                        storage.save(tasks);
                    } catch (DukeException dE) {
                        naruto.say("☹ OOPS!!! The description of a todo cannot be empty.");
                    }
                } else if (input.startsWith("deadline")) {
                    addDeadline(input, tasks, taskCount);
                    taskCount++;
                    storage.save(tasks);
                } else if (input.startsWith("event")) {
                    addEvent(input, tasks, taskCount);
                    taskCount++;
                    storage.save(tasks);
                } else if (input.startsWith("delete")) {
                    taskCount--;
                    delete(Integer.parseInt(input.substring("delete".length() + 1)) - 1, tasks, taskCount);
                    storage.save(tasks);
                } else {
                    throw new DukeException();
                }
            } catch (DukeException dE) {
                naruto.say("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }


    private static void printList(ArrayList<Task> tasks, int taskCount) {
        naruto.say("Here you go");
        naruto.printBar();
        for (int i = 0; i < taskCount; i++) {
            naruto.printIndented((i + 1) + ". [" + tasks.get(i).getTaskIcon() + "]["
                    + tasks.get(i).getStatusIcon() + "] " + tasks.get(i).getDescription()
                    + (tasks.get(i).hasTime() ? ((tasks.get(i) instanceof Deadline ? " (by: " : " (at: ")
                    + tasks.get(i).getTime() + ")") : ""));
        }
        naruto.printBar();
    }

    private static void addToDo(String input, ArrayList<Task> tasks, int taskCount) throws DukeException {
        if (input.length() <= ("todo".length() + 1)) {
            throw new DukeException();
        }
        tasks.add(new ToDo(input.substring("todo".length() + 1)));
        naruto.printTaskAddedMessage("[" + tasks.get(taskCount).getTaskIcon() + "]["
                + tasks.get(taskCount).getStatusIcon() + "] " + tasks.get(taskCount).getDescription(), taskCount);
    }

    private static void addDeadline(String input, ArrayList<Task> tasks, int taskCount) {
        int trigger = input.indexOf('/');
        tasks.add(new Deadline(input.substring("deadline".length() + 1, trigger - 1),
                input.substring(trigger + "/by ".length())));

        naruto.printTaskAddedMessage("[" + tasks.get(taskCount).getTaskIcon() + "]["
                + tasks.get(taskCount).getStatusIcon() + "] " + tasks.get(taskCount).getDescription(), taskCount);
    }

    private static void addEvent(String input, ArrayList<Task> tasks, int taskCount) {
        int trigger = input.indexOf('/');
        tasks.add(new Event(input.substring("event".length() + 1, trigger - 1),
                input.substring(trigger + "/at ".length())));

        naruto.printTaskAddedMessage("[" + tasks.get(taskCount).getTaskIcon() + "]["
                + tasks.get(taskCount).getStatusIcon() + "] " + tasks.get(taskCount).getDescription(), taskCount);
    }

    private static void addDone(int taskNumber, ArrayList<Task> tasks) {
        tasks.get(taskNumber).markAsDone();
        naruto.say("All right, consider it done");
        naruto.printBetweenBars("[" + tasks.get(taskNumber).getStatusIcon() + "] " + tasks.get(taskNumber).getDescription());
    }

    private static void delete(int taskNumber, ArrayList<Task> tasks, int taskCount) {
        naruto.say("Noted. I've removed this task");
        naruto.printBetweenBars("[" + tasks.get(taskNumber).getTaskIcon() + "]["
                + tasks.get(taskNumber).getStatusIcon() + "] " + tasks.get(taskNumber).getDescription());
        naruto.say("Now you have " + taskCount + " tasks in the list");
        tasks.remove(taskNumber);
    }

}
