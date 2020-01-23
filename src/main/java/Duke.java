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
                System.out.println(indent + name + ": See you later!");
                break;
            } else if (input.equals("list")) {
                System.out.println(indent + name + ": Here you go");
                System.out.println(indent + line);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(indent + (i + 1) + ". [" + tasks[i].getTaskIcon() + "]["
                            + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription()
                            + (tasks[i].hasTime() ? ((tasks[i] instanceof Deadline ? " (by: " : " (at: ")
                            + tasks[i].getTime() + ")") : ""));
                }
                System.out.println(indent + line);
            } else if (input.startsWith("done")) {
                int taskNumber = Integer.parseInt(input.substring("done".length() + 1)) - 1;
                tasks[taskNumber].markAsDone();
                System.out.println(indent + name + ": All right, consider it done: ");
                System.out.println(indent + line);
                System.out.println(indent + "[" + tasks[taskNumber].getStatusIcon() + "] " + tasks[taskNumber].getDescription());
                System.out.println(indent + line);
            } else if (input.startsWith("todo")) {
                tasks[taskCount] = new Todo(input.substring("todo".length() + 1));
                System.out.println(indent + name + ": Got it. I've added this task");
                System.out.println(indent + line);
                System.out.println(indent + "[" + tasks[taskCount].getTaskIcon() + "]["
                        + tasks[taskCount].getStatusIcon() + "] " + tasks[taskCount].getDescription());
                System.out.println(indent + line);
                taskCount++;
                System.out.println(indent + name + ": Now you have " + taskCount + " tasks in the list");
            } else if (input.startsWith("deadline")) {
                int trigger = input.indexOf('/');
                tasks[taskCount] = new Deadline(input.substring("deadline".length() + 1, trigger - 1),
                        input.substring(trigger + "/by ".length()));
                System.out.println(indent + name + ": Got it. I've added this task");
                System.out.println(indent + line);
                System.out.println(indent + "[" + tasks[taskCount].getTaskIcon() + "]["
                        + tasks[taskCount].getStatusIcon() + "] " + tasks[taskCount].getDescription()
                        + " (by: " + tasks[taskCount].getTime() + ")");
                System.out.println(indent + line);
                taskCount++;
                System.out.println(indent + name + ": Now you have " + taskCount + " tasks in the list");
            } else if (input.startsWith("event")) {
                int trigger = input.indexOf('/');
                tasks[taskCount] = new Event(input.substring("event".length() + 1, trigger - 1),
                        input.substring(trigger + "/at ".length()));
                System.out.println(indent + name + ": Got it. I've added this task");
                System.out.println(indent + line);
                System.out.println(indent + "[" + tasks[taskCount].getTaskIcon() + "]["
                        + tasks[taskCount].getStatusIcon() + "] " + tasks[taskCount].getDescription()
                        + " (at: " + tasks[taskCount].getTime() + ")");
                System.out.println(indent + line);
                taskCount++;
                System.out.println(indent + name + ": Now you have " + taskCount + " tasks in the list");
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(indent + name + ": \"" + input + "\" added");
            }
        }
    }
}
