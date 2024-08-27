import java.util.*;

public class Counter {

    public static void main(String[] args) {
        TreeMap<String, Integer> treeMap = new TreeMap<>();

        try {
            String str = "a a 12 12 4 2 12";
            String[] words = str.trim().split(" ");

            for(int i = 0; i<words.length; i++) {
                Integer num = treeMap.get(words[i]);
                if (num == null) {
                    treeMap.put(words[i], 1);
                } else {
                    treeMap.put(words[i], num + 1);
                }
            }

            for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
                System.out.println(entry.getKey() + " occurence: " + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}