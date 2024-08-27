public class Pen {

    public Pen() {}

    public void drawShape(Shape s) {
        if(s instanceof Rectangle){
            System.out.println("Area of rectangle: " + ((Rectangle) s).getWidth()* ((Rectangle) s).getHeight());
        } else if(s instanceof Circle){
            System.out.println("Area of circle: " + ((Circle) s).getRadius()* ((Circle) s).getRadius()*Math.PI);
        }
    }

    public void changeColor(Shape s, String color) {
        if(s instanceof Rectangle){
            s.setColor(color);
            System.out.println("Color of circle: " + s.getColor());
        } else {
            s.setColor(color);
            System.out.println("Color of rectangle: " + s.getColor());
        }
    }


    public void changeColorCircle(String color, Circle c) {

    }

    public void changeColorRectangle(String color, Rectangle r) {

    }

}
