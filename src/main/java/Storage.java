import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to store list of tasks
 */
public class Storage {
    private String filePath;

    Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Save list of tasks to hard disk
     * @param tasks list of tasks
     */
    protected void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (int i = 0; i < tasks.size(); i++) {
                fw.write((i + 1) + ". " + tasks.get(i).toString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
