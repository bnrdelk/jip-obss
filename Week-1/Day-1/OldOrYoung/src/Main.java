import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your age: ");

        if (scanner.nextInt() < 50) {
            System.out.println("You are young.");
        } else {
            System.out.println("You are old.");
        }

        scanner.close();
    }
}