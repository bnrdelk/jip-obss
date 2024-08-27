import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("John", 25),
                new Person("Jane", 30),
                new Person("Jake", 28),
                new Person("David", 32),
                new Person("Julia", 27),
                new Person("Mary", 23));

        List<Person> output = people.stream()
                .filter(o -> o.getName().charAt(0) =='J')
                .sorted((p1,p2) -> Integer.compare(p1.getAge(),p2.getAge())).collect(Collectors.toList());

        output.forEach(System.out::println);
        for (Person person : output) {
            System.out.println(person.getAge() + " " + person.getName());
        }
    }
}