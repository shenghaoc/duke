public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public String getTaskIcon() {
        return "T";
    }
}