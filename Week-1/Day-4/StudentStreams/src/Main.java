import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("John", "ceng", 25),
                new Student("Jane", "ee", 30),
                new Student("Jake","ceng", 28),
                new Student("David","ceng", 32),
                new Student("Julia","ee", 27));

        List<Student> hasE = students.stream()
                .filter(o -> o.getName().contains("E"))
                .collect(Collectors.toList());

        for (Student o : hasE) {
            System.out.println("Has E: )");
            System.out.println(o.getName());
        }

        Map<String, List<Student>> groupedByDepartment = students.stream()
                .collect(Collectors.groupingBy(Student::getDepartment));

        System.out.println("EE students: " + groupedByDepartment.get("ee").size());
        System.out.println("CENG students: " + groupedByDepartment.get("ceng").size());

        double eeAvg = groupedByDepartment.get("ee").stream()
                .mapToInt(Student::getGpa)
                .average().getAsDouble();

        double cengAvg = groupedByDepartment.get("ceng").stream()
                .mapToInt(Student::getGpa)
                .average().getAsDouble();

        System.out.println("\nAverage GPA for ee: " + eeAvg);
        System.out.println("Average GPA for ceng: " + cengAvg);

        System.out.println("\nTop 2 ee:");
        groupedByDepartment.get("ee").stream()
                .sorted((s1, s2) -> Integer.compare(s2.getGpa(), s1.getGpa()))
                .limit(2)
                .forEach(student -> System.out.println(student.getName() + " GPA: " + student.getGpa()));

        System.out.println("\nTop 2 ceng:");
        groupedByDepartment.get("ceng").stream()
                .sorted((s1, s2) -> Integer.compare(s2.getGpa(), s1.getGpa()))
                .limit(2)
                .forEach(student -> System.out.println(student.getName() + " GPA: " + student.getGpa()));

    }
}