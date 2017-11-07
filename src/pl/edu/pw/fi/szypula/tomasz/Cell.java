/**
 *
 */
package pl.edu.pw.fi.szypula.tomasz;


import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author longman
 */
public class Cell implements Drawable{

    private List<Ball> balls = new ArrayList<>();
    private List<Cell> cells = new ArrayList<>();
    private  boolean isWall;
    private BoundingBox boundingBox;
    private Vector2D center;

    public boolean isWall() {
        return isWall;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell(boolean isWall,BoundingBox boundingBox) {
        //this.isWall = isWall;
        setWall(isWall);
        this.boundingBox=boundingBox;
        center = new Vector2D(boundingBox.getMinX()+boundingBox.getWidth()/2,boundingBox.getMinY()+boundingBox.getHeight()/2);

    }
    public void setWall(boolean isWall){
        this.isWall=isWall;
        if(isWall()){
            System.out.println("dodajemy sciane");
            balls.clear();
            balls.add(new Ball(
                    new Vector2D(center.getX(),center.getY()),
                    boundingBox.getWidth()/2,
                    new Vector2D(0,0),
                    true));
        }
        System.out.println(balls);
    }
    public void insertElement(Ball element) {
        if(!isWall())
        balls.add(element);
    }

    public List<Ball> getBalls() { return balls;  }

    public void addNeighbour(Cell cell) {
        cells.add(cell);
    }


    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.GRAY);
        if(isWall())
        graphicsContext.fillRect(
                boundingBox.getMinX(),
                boundingBox.getMinY(),
                boundingBox.getWidth(),
                boundingBox.getHeight()
        );
        for (Ball wall:
             this.getBalls()   ) {
            wall.draw(graphicsContext);

        }
    }

    @Override
    public String toString() {
        return "Cell : "+center.toString();
    }

    public void clear() {
        if (!this.isWall())
        this.getBalls().clear();
    }
}
