package pl.edu.pw.fi.szypula.tomasz;

public class Vector2D {

    private double x = 0;
    private double y = 0;

    public Vector2D(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2D(final Vector2D v) {
        this(v.x, v.y);
    }

    public Vector2D(Vector2D v1, Vector2D v2) {
        x = v2.x - v1.x;
        y = v2.y - v1.y;
    }

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
