public class Main {
    public static void main(String[] args) {
        Pen pen = new Pen();
        Shape r = new Rectangle(5,5,"white");
        Shape c = new Circle(4,"purple");

        pen.changeColor(r, "pink");
        pen.changeColor(c, "blue");

        pen.drawShape(c);
        pen.drawShape(r);

    }
}