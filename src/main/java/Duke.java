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
                System.out.println("Naruto: See you later!");
                break;
            } else if (input.equals("list")) {
                System.out.println("Naruto: Here you go");
                System.out.println(indent + line);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(indent + (i + 1) + ". [" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
                }
                System.out.println(indent + line);
            } else if (input.startsWith("done")) {
                int taskNumber = Integer.parseInt(input.substring(5)) - 1;
                tasks[taskNumber].markAsDone();
                System.out.println("Naruto: All right, consider it done: ");
                System.out.println(indent + line);
                System.out.println(indent + "[" + tasks[taskNumber].getStatusIcon() + "] " + tasks[taskNumber].getDescription());
                System.out.println(indent + line);
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("Naruto: \"" + input + "\" added");
            }
        }
    }
}
