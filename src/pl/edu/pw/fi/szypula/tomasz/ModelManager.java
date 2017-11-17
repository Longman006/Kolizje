package pl.edu.pw.fi.szypula.tomasz;

import colorfulcircles.Circles;
import javafx.geometry.BoundingBox;
import javafx.stage.Screen;
import sun.misc.Perf;


import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by longman on 07.05.17.
 */
public class ModelManager {

    private List<Ball> ballList = new ArrayList<>();
    private Grid grid;
    private Vector2D pointMin;
    private Vector2D pointMax;
    private double maxDeltaPosition;
    private BoundingBox boundingBox;
    private double deltaTime;

    public List<Ball> getBallList() {
        return ballList;
    }

    public Grid getGrid() {
        return grid;
    }

    private CollisionManager collisionManager;
    private int numberOfBalls;

    public ModelManager(Vector2D pointMin, Vector2D pointMax, double maxDeltaPosition, double deltaTime) {
        this.pointMin = pointMin;
        this.pointMax = pointMax;
        this.maxDeltaPosition = maxDeltaPosition;
        this.deltaTime = deltaTime;
        numberOfBalls = 500;
        Vector2D size = new Vector2D(pointMin,pointMax);
        boundingBox=new BoundingBox(pointMin.getX(),pointMin.getY(),size.getX(),size.getY());
        //generateStuff(numberOfBalls);
        testBalls();
    }

    private void testBalls() {

        Vector2D position= Vector2DMath.vector2DSum(pointMax,pointMin).multiply(1.0/2.0);
        Vector2D velocity = new Vector2D(2,-1);
        Ball ball=new Ball(position, 15, velocity);

        //System.out.println("one ball "+ball);

        ballList.add(ball);
        ball = new Ball(new Vector2D(Vector2DMath.vector2DSum(position,new Vector2D(100,0))),15,new Vector2D(-2,-1));
        ballList.add(ball);

        ball = new Ball(new Vector2D(Vector2DMath.vector2DSum(position,new Vector2D(0,100))),15,new Vector2D(-2,-1));

        ballList.add(ball);
        ball = new Ball(new Vector2D(Vector2DMath.vector2DSum(position,new Vector2D(100,100))),15,new Vector2D(5,-1));
        //
        ballList.add(ball);

        generateGrid();
        generateWalls();
        generateCollisionManager();
    }

    private void generateWalls() {
        List<Cell> borderCells = grid.getBorderCells();
        for(Cell cell : borderCells){
            cell.setWall(true);
        }
    }

    Set<Pair<Ball>> getCollisionPairs(){
        return collisionManager.getCollisionPairs();
    }


    private void generateStuff(int N){
        double x, y,r = 4;
        double time;
        Vector2D point, velocity = new Vector2D();
        for (int i = 0; i < N; i++) {
            x = Math.random();
            y = Math.random();
            point = new Vector2D(x * (pointMax.getX() ), y * (pointMax.getY()));
            velocity = new Vector2D(x, y);
            ballList.add( new Ball(point, r, velocity));
        }
        generateGrid();
        generateCollisionManager();


    }
    private void generateGrid(){

        grid = new Grid(boundingBox, maxDeltaPosition);
        PerformanceManager.startMeasurement();
        grid.fillGrid(ballList);
        double time = PerformanceManager.endMeasurement() ;
        System.out.println("fillGrid : time elapsed (mili) : " + time);
    }
    private void generateCollisionManager(){

        collisionManager = new CollisionManager(grid,deltaTime);
        PerformanceManager.startMeasurement();
        createCollisionPairs();
        double time = PerformanceManager.endMeasurement();
       // System.out.println("collisionPairs :  time elapsed (mili) :" + time);
        //collisionManager.printSets();
    }

    public void staticCollision() {
        collisionManager.staticCollision();
    }
    public void dynamicCollsion(){collisionManager.dynamicCollision();}
    public void updatePosition(double deltaTime){
        Vector2D newPosition ;
        for (Ball ball:getBallList()) {
            newPosition=Vector2DMath.vector2DSum(ball.getPosition(),Vector2DMath.multiplyVector2D(ball.getVelocity(),deltaTime));

            //System.out.println("Moving ball "+ball);
           // System.out.println("To "+ newPosition);
            ball.setPosition(newPosition);
        }
    }
    public void createCollisionPairs(){
        grid.fillGrid(ballList);
        collisionManager.createCollisionPairs();
    }
}
