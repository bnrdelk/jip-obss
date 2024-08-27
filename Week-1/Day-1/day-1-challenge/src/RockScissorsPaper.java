import java.util.Random;
import java.util.Scanner;

public class RockScissorsPaper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String move = "";

        // points
        int computerPoint = 0, playerPoint = 0;

        while(computerPoint != 3 && playerPoint != 3) {
            System.out.print("Enter your move: ");
            move = scanner.nextLine().toUpperCase();

            // computer's move
            String[] moves = {"R", "S", "P"};
            Random random = new Random();
            int randomIndex = random.nextInt(3);
            String computersMove = moves[randomIndex];

            System.out.print("Computer's move was: " + computersMove + ". ");

            switch (move) {
                case "R" -> {
                    if (computersMove.equals("S")) {
                        System.out.println("You win this round.");
                        playerPoint++;
                    } else if (computersMove.equals("R")) {
                        System.out.println("It is a tie!");
                    } else {
                        System.out.println("You lost this round.");
                        computerPoint++;
                    }

                    System.out.println("\n*********************\n" +
                            "Player " + playerPoint + "-" + computerPoint + " Computer\n" +
                            "*********************\n");
                }
                case "S" -> {
                    if (computersMove.equals("P")) {
                        System.out.println("You win this round.");
                        playerPoint++;
                    } else if (computersMove.equals("S")) {
                        System.out.println("It is a tie!");
                    } else {
                        System.out.println("You lost this round.");
                        computerPoint++;
                    }

                    System.out.println("\n*********************\n" +
                            "Player " + playerPoint + "-" + computerPoint + " Computer\n" +
                            "*********************\n");
                }
                case "P" -> {
                    if (computersMove.equals("S")) {
                        System.out.println("You win this round.");
                        playerPoint++;
                    } else if (computersMove.equals("P")) {
                        System.out.println("It is a tie!");
                    } else {
                        System.out.println("You lost this round.");
                        computerPoint++;
                    }

                    System.out.println("\n*********************\n" +
                            "Player " + playerPoint + "-" + computerPoint + " Computer\n" +
                            "*********************\n");
                }
                default -> {
                    System.out.println("But you didn't enter a valid move. (enter r/R,s/S or p/P)\n");
                }
            }
        }

        if(playerPoint == 3) {
            System.out.println("You win the game!");
        } else {
            System.out.println("Computer win the game!");
        }

    }
}
