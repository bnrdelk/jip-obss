public class Square extends Shape{
    private double edge;

    public Square(double edge) {
        this.edge = edge;
    }

    @Override
    public double calculateArea() {
        return Math.pow(edge, 2);
    }

    public double getEdge() {
        return edge;
    }

    public void setEdge(double edge) {
        this.edge = edge;
    }
}
