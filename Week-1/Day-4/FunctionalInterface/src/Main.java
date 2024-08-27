import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> sampleList = List.of("a", "ababa", "ceng", "beyza", "nur", "deliktaş");

        ListFilter removeShorterStrings = (a, list) -> {
            int minLength = Integer.parseInt(a);
            List<String> filteredList = list.stream()
                    .filter(str -> str.length() >= minLength)
                    .collect(Collectors.toList());

            System.out.println("Strings longer than " + minLength + ": " + filteredList);
            return !filteredList.isEmpty();
        };

        ListFilter startingWith = (a, list) -> {
            List<String> filteredList = list.stream()
                    .filter(str -> str.startsWith(a))
                    .collect(Collectors.toList());
            System.out.println("Strings starting with '" + a + "': " + filteredList);
            return !filteredList.isEmpty();
        };

    /*
        ListFilter filterList = (a, list) -> {
            return list.contains(a);
        };


        ListFilter isContain = (x, list) -> {
            for (String s : list) {
                if (s.contains(x)) {
                    return true;
                }
            }
            return false;
        };

    }*/

        ListFilter removeShorterStrings = (size, list) -> {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).length() < size) {
                    list.remove(i);
                }
            }
            return list;
        };

        ListFilter startingWith = (x, list) -> {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).charAt(0) == x) {
                    list.remove(i);
                }
            }
            return list;
        };

    }
}