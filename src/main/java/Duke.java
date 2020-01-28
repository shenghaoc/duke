import java.util.Scanner;

public class Duke {

    private static final String FILE_PATH = "data/duke.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ui naruto = new Ui("Naruto");
        Storage storage = new Storage(FILE_PATH);
        TaskList taskList = new TaskList();
        Parser parser = new Parser();

        label:
        for (; ; ) {
            String input = sc.nextLine();
            try {
                switch (parser.parse(input)) {
                    case BYE:
                        naruto.say("See you later!");
                        break label;
                    case LIST:
                        naruto.printList(taskList.getUpdatedTasks());
                        break;
                    case DONE: {
                        int taskNumber = Integer.parseInt(input.substring("done".length() + 1)) - 1;
                        taskList.addDone(taskNumber);
                        naruto.say("All right, consider it done");
                        naruto.printBetweenBars("[" + taskList.getStatusIcon(taskNumber) + "] "
                                + taskList.getDescription(taskNumber));
                        storage.save(taskList.getUpdatedTasks());
                        break;
                    }
                    case TODO:
                        try {
                            int taskNumber = taskList.addToDo(input);
                            naruto.printTaskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                                    + taskList.getStatusIcon(taskNumber) + "] "
                                    + taskList.getDescription(taskNumber), taskNumber);
                            storage.save(taskList.getUpdatedTasks());
                        } catch (DukeException dE) {
                            naruto.say("☹ OOPS!!! The description of a todo cannot be empty.");
                        }
                        break;
                    case DEADLINE: {
                        int taskNumber = taskList.addDeadline(input);
                        naruto.printTaskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                                + taskList.getStatusIcon(taskNumber) + "] "
                                + taskList.getDescription(taskNumber), taskNumber);
                        storage.save(taskList.getUpdatedTasks());
                        break;
                    }
                    case EVENT: {
                        int taskNumber = taskList.addEvent(input);
                        naruto.printTaskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                                + taskList.getStatusIcon(taskNumber) + "] "
                                + taskList.getDescription(taskNumber), taskNumber);

                        storage.save(taskList.getUpdatedTasks());
                        break;
                    }
                    case DELETE: {
                        int taskNumber = Integer.parseInt(input.substring("delete".length() + 1)) - 1;
                        naruto.say("Noted. I've removed this task");
                        naruto.printBetweenBars("[" + taskList.getTaskIcon(taskNumber) + "]["
                                + taskList.getStatusIcon(taskNumber) + "] " + taskList.getDescription(taskNumber));
                        naruto.say("Now you have " + (taskList.getTaskCount() - 1) + " tasks in the list");
                        taskList.delete(taskNumber);
                        storage.save(taskList.getUpdatedTasks());
                        break;
                    }
                    case FIND:
                        // Test merging
                        naruto.printMatchingItems(taskList.getUpdatedTasks(), input.substring("find".length() + 1));
                        break;
                }
            } catch (DukeException dE) {
                naruto.say("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }
}
