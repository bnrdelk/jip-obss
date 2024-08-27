import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double base = 0, height = 0, edge = 0, radius = 0;

        while (true) {
            System.out.println("-------------------------------------------------");
            System.out.print("Choose the shape that you want to calculate area: \n" +
                    "1- Triangular\n" +
                    "2- Square\n" +
                    "3- Rectangular\n" +
                    "4- Circle\n" +
                    "5- Exit\n" +
                    ">"
            );

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Base length: \n>");
                    base = scanner.nextInt();
                    System.out.print("Height length: \n>");
                    height = scanner.nextInt();

                    Triangular triangular = new Triangular(base, height);
                    System.out.println("Result: " + triangular.calculateArea());
                }
                case 2 -> {
                    System.out.print("Edge length: \n>");
                    edge = scanner.nextInt();

                    Square square = new Square(edge);
                    System.out.println("Result: " + square.calculateArea());
                }
                case 3 -> {
                    System.out.print("Long edge length: \n>");
                    edge = scanner.nextInt();

                    System.out.print("Small edge length: \n>");
                    height = scanner.nextInt();

                    Rectangle rectangle = new Rectangle(edge, height);
                    System.out.println("Result: " + rectangle.calculateArea());
                }
                case 4 -> {
                    System.out.print("Radius length: \n>");
                    radius = scanner.nextInt();

                    if (radius < 0) {
                        System.out.println("Negative radius is not allowable");
                    }

                    Circle circle = new Circle(radius);
                    System.out.println("Result: " + circle.calculateArea());
                }
                case 5 -> {
                    System.out.println("Exiting..");
                    return;
                }
                default -> {
                    System.out.println("Please choose a valid shape number (1-4)");
                }
            }
                System.out.println("-------------------------------------------------\n");
            }
    }
}