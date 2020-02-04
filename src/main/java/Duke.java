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

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    String getResponse(String input) {
        Ui naruto = new Ui("Naruto");
        Storage storage = new Storage(FILE_PATH);
        TaskList taskList = new TaskList();
        Parser parser = new Parser();

        try {
            int taskNumber;
            StringBuilder message;
            switch (parser.parse(input)) {
                case BYE:
                    return naruto.say("See you later!");
                case LIST:
                    return naruto.list(taskList.getUpdatedTasks());
                case DONE:
                    taskNumber = Integer.parseInt(input.substring("done".length() + 1)) - 1;
                    taskList.addDone(taskNumber);

                    message = new StringBuilder(naruto.say("All right, consider it done"));
                    message.append(naruto.format("[" + taskList.getStatusIcon(taskNumber) + "] "
                            + taskList.getDescription(taskNumber)));
                    storage.save(taskList.getUpdatedTasks());
                    return message.toString();
                case TODO:
                    try {
                        taskNumber = taskList.addToDo(input);
                        message = new StringBuilder(naruto.taskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                                + taskList.getStatusIcon(taskNumber) + "] "
                                + taskList.getDescription(taskNumber), taskNumber));
                        storage.save(taskList.getUpdatedTasks());
                        return message.toString();
                    } catch (DukeException dE) {
                        return naruto.say("☹ OOPS!!! The description of a todo cannot be empty.");
                    }
                case DEADLINE:
                    taskNumber = taskList.addDeadline(input);
                    message = new StringBuilder();
                    message.append(naruto.taskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                            + taskList.getStatusIcon(taskNumber) + "] "
                            + taskList.getDescription(taskNumber), taskNumber));
                    storage.save(taskList.getUpdatedTasks());
                    return message.toString();
                case EVENT:
                    taskNumber = taskList.addEvent(input);
                    message = new StringBuilder();
                    message.append(naruto.taskAddedMessage("[" + taskList.getTaskIcon(taskNumber) + "]["
                            + taskList.getStatusIcon(taskNumber) + "] "
                            + taskList.getDescription(taskNumber), taskNumber));

                    storage.save(taskList.getUpdatedTasks());
                    return message.toString();
                case DELETE:
                    taskNumber = Integer.parseInt(input.substring("delete".length() + 1)) - 1;
                    message = new StringBuilder();
                    message.append(naruto.say("Noted. I've removed this task"));
                    message.append(naruto.format("[" + taskList.getTaskIcon(taskNumber) + "]["
                            + taskList.getStatusIcon(taskNumber) + "] " + taskList.getDescription(taskNumber)));
                    message.append(naruto.say("Now you have " + (taskList.getTaskCount() - 1) + " tasks in the list"));
                    taskList.delete(taskNumber);
                    storage.save(taskList.getUpdatedTasks());
                    return message.toString();
                case FIND:
                    // Test merging
                    return naruto.matchingItems(taskList.getUpdatedTasks(), input.substring("find".length() + 1));
            }
        } catch (DukeException dE) {
            return naruto.say("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        return "Duke heard: " + input;
    }
}
