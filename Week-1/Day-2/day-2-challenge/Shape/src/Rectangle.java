public class Rectangle extends Shape {
    private double height;
    private double width;

    public Rectangle(double width, double height) {
        this.height = height;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return height*width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
