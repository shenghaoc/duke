import java.util.Scanner;

public class Duke {

    private static final String FILE_PATH = "data/duke.txt";
    private static Ui naruto;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        naruto = new Ui("Naruto");
        Storage storage = new Storage(FILE_PATH);
        TaskList taskList = new TaskList();

        for (; ; ) {
            String input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    naruto.say("See you later!");
                    break;
                } else if (input.equals("list")) {
                    naruto.printList(taskList.getUpdatedTasks());
                } else if (input.startsWith("done")) {
                    int taskNumber = Integer.parseInt(input.substring("done".length() + 1)) - 1;
                    taskList.addDone(taskNumber);
                    naruto.say("All right, consider it done");
                    naruto.printBetweenBars("[" + taskList.getStatusIcon(taskNumber) + "] "
                            + taskList.getDescription(taskNumber));
                    storage.save(taskList.getUpdatedTasks());
                } else if (input.startsWith("todo")) {
                    try {
                        int taskNumber = taskList.addToDo(input);
                        naruto.printTaskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                                + taskList.getStatusIcon(taskNumber) + "] "
                                + taskList.getDescription(taskNumber), taskNumber);
                        storage.save(taskList.getUpdatedTasks());
                    } catch (DukeException dE) {
                        naruto.say("☹ OOPS!!! The description of a todo cannot be empty.");
                    }
                } else if (input.startsWith("deadline")) {
                    int taskNumber = taskList.addDeadline(input);
                    naruto.printTaskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                            + taskList.getStatusIcon(taskNumber) + "] "
                            + taskList.getDescription(taskNumber), taskNumber);
                    storage.save(taskList.getUpdatedTasks());
                } else if (input.startsWith("event")) {
                    int taskNumber = taskList.addEvent(input);
                    naruto.printTaskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                            + taskList.getStatusIcon(taskNumber) + "] "
                            + taskList.getDescription(taskNumber), taskNumber);

                    storage.save(taskList.getUpdatedTasks());
                } else if (input.startsWith("delete")) {
                    int taskNumber = Integer.parseInt(input.substring("delete".length() + 1)) - 1;
                    naruto.say("Noted. I've removed this task");
                    naruto.printBetweenBars("[" + taskList.getTaskIcon(taskNumber) + "]["
                            + taskList.getStatusIcon(taskNumber) + "] " + taskList.getDescription(taskNumber));
                    naruto.say("Now you have " + (taskList.getTaskCount() - 1) + " tasks in the list");
                    taskList.delete(taskNumber);
                    storage.save(taskList.getUpdatedTasks());
                } else {
                    throw new DukeException();
                }
            } catch (DukeException dE) {
                naruto.say("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }
}
