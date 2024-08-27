import java.util.Scanner;

public class Diamond {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a size (Negative input to exit): ");
            int size = scanner.nextInt();

            // Exit
            if (size < 0) {
                break;
            }

            // if the size is even transform to odd one
            if (size % 2 == 0) {
                size++;
            }

            int n = size / 2; // to predict the line for mirroring

            for (int i = 0; i <= n; i++) {
                for (int j = 0; j < n - i; j++) {
                    System.out.print(" ");
                }

                for (int j = 0; j < 2 * i + 1; j++) {
                    System.out.print("*");
                }
                System.out.println();
            }

            // lower part of the diamond
            for (int i = n - 1; i >= 0; i--) {
                for (int j = 0; j < n - i; j++) {
                    System.out.print(" ");
                }

                for (int j = 0; j < 2 * i + 1; j++) {
                    System.out.print("*");
                }

                System.out.println();
            }
            System.out.println("--------------------");
        }

        scanner.close();
    }
}
