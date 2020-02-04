import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The main entry point for the chat bot containing the main method
 */
public class Duke {

    private static final String FILE_PATH = "data/duke.txt";
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image user = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));


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
                int taskNumber;
                switch (parser.parse(input)) {
                    case BYE:
                        naruto.say("See you later!");
                        break label;
                    case LIST:
                        naruto.printList(taskList.getUpdatedTasks());
                        break;
                    case DONE:
                        taskNumber = Integer.parseInt(input.substring("done".length() + 1)) - 1;
                        taskList.addDone(taskNumber);
                        naruto.say("All right, consider it done");
                        naruto.printBetweenBars("[" + taskList.getStatusIcon(taskNumber) + "] "
                                + taskList.getDescription(taskNumber));
                        storage.save(taskList.getUpdatedTasks());
                        break;
                    case TODO:
                        try {
                            taskNumber = taskList.addToDo(input);
                            naruto.printTaskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                                    + taskList.getStatusIcon(taskNumber) + "] "
                                    + taskList.getDescription(taskNumber), taskNumber);
                            storage.save(taskList.getUpdatedTasks());
                        } catch (DukeException dE) {
                            naruto.say("☹ OOPS!!! The description of a todo cannot be empty.");
                        }
                        break;
                    case DEADLINE:
                        taskNumber = taskList.addDeadline(input);
                        naruto.printTaskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                                + taskList.getStatusIcon(taskNumber) + "] "
                                + taskList.getDescription(taskNumber), taskNumber);
                        storage.save(taskList.getUpdatedTasks());
                        break;
                    case EVENT:
                        taskNumber = taskList.addEvent(input);
                        naruto.printTaskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                                + taskList.getStatusIcon(taskNumber) + "] "
                                + taskList.getDescription(taskNumber), taskNumber);

                        storage.save(taskList.getUpdatedTasks());
                        break;
                    case DELETE:
                        taskNumber = Integer.parseInt(input.substring("delete".length() + 1)) - 1;
                        naruto.say("Noted. I've removed this task");
                        naruto.printBetweenBars("[" + taskList.getTaskIcon(taskNumber) + "]["
                                + taskList.getStatusIcon(taskNumber) + "] " + taskList.getDescription(taskNumber));
                        naruto.say("Now you have " + (taskList.getTaskCount() - 1) + " tasks in the list");
                        taskList.delete(taskNumber);
                        storage.save(taskList.getUpdatedTasks());
                        break;
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

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    String getResponse(String input) {
        return "Duke heard: " + input;
    }
}
