import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int randomNum = random.nextInt(100) + 1;
        int step = 0;
        String flag = "unsuccessful";
        int guessedNum = -1;

        System.out.println("Try to guess random num in max 5 step: ");
        while(step < 5) {
             guessedNum = sc.nextInt();

            if( guessedNum > 100){
                System.out.println("Can't be greater than 100, guess again: ");
                guessedNum = sc.nextInt();
            }
            step ++;

            if(guessedNum > randomNum) {
                System.out.println("It is too high");
            } else if(guessedNum < randomNum) {
                System.out.println("It is too low");
            } else {
                System.out.println("Gotcha");
                flag = "successful";
                break;
            }

        }

        System.out.println("Your result is: " + flag + " it was " + randomNum);
    }
}