import java.util.Scanner;

public class MultiplicationTable {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int selectedNum = 0;

        while(true) {
            System.out.print("Enter a number (0 for exit): ");
            selectedNum = scanner.nextInt();

            if(selectedNum == 0)
            {
                System.out.println("Exiting the game..");
                break;
            }

            for(int i = 1; i < 10; i++) {
                System.out.println(selectedNum + " * " + i + " = " + selectedNum*i);
            }

            System.out.println("\n---------------------\n");
        }

    }
}