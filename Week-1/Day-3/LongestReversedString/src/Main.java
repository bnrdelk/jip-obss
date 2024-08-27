import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String sentence = scanner.nextLine();

        String[] words = sentence.split(" ");
        System.out.println(Arrays.toString(words));

        int maxSize = 0;
        String longestWord = words[0];

        for (String word : words) {
            word.trim();
            if (word.length() > maxSize) {
                maxSize = word.length();
                //System.out.println(longestWord);
                //System.out.println(maxSize);
                longestWord = word;
            }
        }

        System.out.println(longestWord);

        StringBuilder sb = new StringBuilder();

        for(int i = longestWord.length()-1; i>=0; i--) {
            sb.append(longestWord.charAt(i));
        }

        System.out.println(sb);
    }
}