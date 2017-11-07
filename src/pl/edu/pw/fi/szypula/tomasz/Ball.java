package pl.edu.pw.fi.szypula.tomasz;


import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import trash.Element;

public class Ball implements Drawable {

    private Vector2D center;
    private double radius;
    private Vector2D velocity;
    BoundingBox boundingBox;
    private boolean isWall;
    private double mass;


    public Ball(Vector2D center, double radius, Vector2D initVelocity) {
        this.center = center;
        this.radius = radius;
        this.velocity = initVelocity;
        boundingBox=new BoundingBox(center.getX()-radius,center.getY()-radius,2*radius,2*radius);
        isWall = false;
        mass = 1*radius*radius;
    }
    public Ball(Vector2D center, double radius, Vector2D initVelocity,boolean isWall) {
        this(center,radius,initVelocity);
        this.isWall = isWall;
    }

    public Ball() {
        this(new Vector2D(),0,new Vector2D());
    }

    public double getBottom(){
        return this.getY()+this.getRadius();
    }
    public double getTop(){
        return this.getY()-this.getRadius();
    }
    public double getRight(){
        return this.getX()+this.getRadius();
    }
    public double getLeft(){
        return this.getX()-this.getRadius();
    }


    public double getX() {
        return center.getX();
    }


    public double getY() {
        return center.getY();
    }


    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public double getRadius() {
        return radius;
    }

    public void setX(double x) {
        center.setX(x);
    }

    public void setY(double y) {
        center.setY(y);
    }

    public boolean isWall(){ return isWall;}
    public void isWall(boolean isWall){ this.isWall=isWall;}


    public Vector2D getPosition() {
        return center;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "center=" + center +
                ", radius=" + radius +
                ", velocity=" + velocity +
                ",isWall="+isWall()+
                 '}';
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillOval(getX()-radius,getY()-radius,getRadius()*2,getRadius()*2);
       // graphicsContext.setFill(Color.BLUE);
        //graphicsContext.setStroke(Color.BLACK);
       // graphicsContext.fillRect(getBoundingBox().getMinX(),getBoundingBox().getMinY(),getBoundingBox().getWidth(),getBoundingBox().getHeight());
       // graphicsContext.setFill(Color.BLACK);
        //graphicsContext.fillRect(getBoundingBox().getMinX(),getBoundingBox().getMinY(),5,5);

    }

    public void setPosition(Vector2D ballPosition) {
        getPosition().setPosition(ballPosition);
    }

    public  void shiftPosition(Vector2D shift){getPosition().addVector2D(shift);}

    public  void shiftVelocity(Vector2D shift){getVelocity().addVector2D(shift);}

    public Vector2D getVelocity() {
        return velocity;
    }

    public double getMass() {
        return mass;
    }

    public void setVelocity(Vector2D velocity) {
        this.getVelocity().setPosition(velocity);
    }

    public double getRadiusSquared() {
        return radius*radius;
    }
}
