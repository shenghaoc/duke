import java.util.Scanner;

public class Duke {

    private static final String name = "Naruto";
    private static final Scanner sc = new Scanner(System.in);
    private static final String line = "____________________________________________________________";
    private static final String indent = "    ";

    public static void main(String[] args) {
        System.out.println("Hi! I'm " + name);
        System.out.println("How may I help you?");

        String[] tasks = new String[100];
        int taskCount = 0;
        for (; ; ) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                System.out.println("Naruto: See you later!");
                break;
            } else if (input.equals("list")) {
                System.out.println("Naruto:");
                System.out.println(indent + line);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(indent + (i + 1) + ". " + tasks[i]);
                }
                System.out.println(indent + line);
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("Naruto: \"" + input + "\" added");
            }
        }
    }
}
