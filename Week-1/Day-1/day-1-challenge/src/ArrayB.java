import java.util.Arrays;
import java.util.Scanner;

public class ArrayB {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = 0;

        while(true) {
            System.out.print("Enter the size of array (0 for exiting): ");
            size = sc.nextInt();

            if(size == 0) {
                System.out.println("Exiting..");
                break;
            }

            int[] arr = new int[size];
            System.out.println("Enter " + size + " numbers for sorting: ");

            for(int i = 0; i < size; i++) {
                arr[i] = sc.nextInt();
            }

            System.out.println("Created array: ");
            printArray(arr);

            Arrays.sort(arr);

            System.out.println("Sorted array: ");
            printArray(arr);
        }
    }

    private static void printArray(int[] array) {
        for (int k : array) {
            System.out.print(k + " ");
        }
        System.out.println();
    }
}

