package pl.edu.pw.fi.szypula.tomasz;


public class Circle implements Element{

    private Vector2D center;
    private double radius;
    private Vector2D velocity;

    public Circle(Vector2D center, double radius, Vector2D initVelocity) {
        this.center = center;
        this.radius = radius;
        this.velocity = initVelocity;
    }

    @Override
    public double getX() {
        return center.getX();
    }

    @Override
    public double getY() {
        return center.getY();
    }

    public void setX(double x) {
        center.setX(x);
    }

    public void setY(double y) {
        center.setY(y);
    }

    @Override
    public Vector2D getPosition() {
        return center;
    }

    @Override
    public String toString() {
        return "Circle pos :" + center.toString() + " r : " + radius + " V : " + velocity;

    }

}
