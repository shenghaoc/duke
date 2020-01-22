import java.util.Scanner;

public class Duke {

    private static final String name = "Naruto";
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Hi! I'm " + name);
        System.out.println("How may I help you?");

        for (; ; ) {
            String input = sc.next();
            if (input.equals("bye")) {
                System.out.println("Naruto: See you later!");
                break;
            }
            System.out.println("Naruto: " + input);
        }
    }
}