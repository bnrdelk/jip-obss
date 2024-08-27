import java.util.Scanner;

public class ArrayA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = 0;

        while(true) {
            // create array
            System.out.print("Enter the size of array you want to reverse (Enter 0 for exit): ");
            size = scanner.nextInt();

            if(size == 0) {
                System.out.println("Exiting..");
                break;
            }

            int[] array = new int[size];

            // init the elements of the array
            System.out.print("Enter the elements : ");
            for (int i = 0; i < array.length; i++) {
                array[i] = scanner.nextInt();
            }

            // print the before and after reverse
            System.out.println("Created array: ");
            printArray(array);

            int[] reversedArray = new int[array.length];
            for (int j = 0; j < array.length; j++) {
                reversedArray[array.length - 1 - j] = array[j];
            }

            System.out.println("Reversed array: ");
            printArray(reversedArray);
        }
    }

    private static void printArray(int[] array) {
        for (int k : array) {
            System.out.print(k + " ");
        }
        System.out.println();
    }
}
