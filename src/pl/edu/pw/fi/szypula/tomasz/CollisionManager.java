package pl.edu.pw.fi.szypula.tomasz;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.*;

/**
 * Created by longman on 02.05.17.
 */
public class CollisionManager {

    private Set<Pair<Cell>> cellPairs = new HashSet<Pair<Cell>>();
    private Set<Pair<Ball>> collisionPairs = new HashSet<Pair<Ball>>();
    private Grid grid;
    private double deltaTime =0;
    private Set<Pair<Drawable>> drawables = new HashSet<>();
    private EnergyMomentumConservationSolver velocitySolver = new EnergyMomentumConservationSolver();

    public Set<Pair<Ball>> getCollisionPairs() {
        return collisionPairs;
    }

    public void printSets(){
        //System.out.println("Set cell pairs : "+cellPairs.size());
        //System.out.println("Set collision pairs : "+collisionPairs.size());
        for (Pair<Ball> ballPair:collisionPairs) {
               // System.out.println(ballPair);
            }
    }
    public CollisionManager(Grid grid,double deltaTime) {
        this.grid = grid;
        this.deltaTime = deltaTime;
        createCellPairs();
    }

    private void createCellPairs(){
        //System.out.println("Creating cell pairs");
        cellPairs.clear();
        for (Cell cell: grid.getCellsList()) {
            for (Cell cellNeighbour: cell.getCells() ) {
                cellPairs.add(new Pair<Cell>(cell,cellNeighbour));

            }
        }
        //System.out.println("Exiting cell pairs");
    }

    public void createCollisionPairs(){
       //System.out.println("Creating collision pairs");
        Ball ball1 = new Ball(new Vector2D(),0,new Vector2D());
        Ball ball2 = ball1;
        collisionPairs.clear();
        //System.out.println();
        for (Pair<Cell> cellPair:cellPairs ) {
            //System.out.println(cellPair);
            Set<Ball> balls = new HashSet<>();
            for (Cell cell:cellPair) {
                balls.addAll(cell.getBalls());
            }
                Ball[] ballArray = balls.toArray(new Ball[balls.size()]);
            for (int i = 0; i < ballArray.length; i++) {
                for(int j = i+1 ; j<ballArray.length; j++) {
                    ball1 = ballArray[i];
                    ball2 = ballArray[j];
                    if(ball1.isWall() && ball2.isWall())
                        continue;
                   // System.out.println("adding : "+ball1+ball2);
                    collisionPairs.add(new Pair<Ball>(ball1, ball2));
                }
        }

        }
        //System.out.println("Exiting collision pairs");
    }

    public  void staticCollision() {
        Vector2D distanceVector;
        Vector2D collisionPoint;
        double distance;
        double radiiSquared;
        double r1;
        double r2;
        Ball ball1,ball2;

        for (Pair<Ball> ballPair : collisionPairs){

            ball1 = ballPair.getFirst();
            ball2 = ballPair.getSecond();
            if(ball1.isWall() && ball2.isWall()){
                System.out.println("Obie sa scianami");
                continue;
            }
            distanceVector = new Vector2D(ball1.getPosition(),ball2.getPosition());
            distance = distanceVector.getLengthSquare();
            r1 = ball1.getRadius();
            r2 = ball2.getRadius();
            radiiSquared =  (r1+r2)*(r1+r2);

            //System.out.println("Distance squared"+distance);
            /**
             * Nie nachodzą na siebie
             */
            if(distance>radiiSquared){
                continue;
            }
            System.out.println("Collision detected ball pair "+ballPair);


            if(ball2.isWall() ) {
                System.out.println("Jestem tu ");
                wallCollision(ball2,ball1);
            }
            else if (ball1.isWall()){
                System.out.println("Jestem tutaj ");
                wallCollision(ball1,ball2);
            }
            else{
                distanceVector.normalize();
                collisionPoint = calculateCollisionPoint(ball1,ball2);
                correctPosition(ballPair,collisionPoint,distanceVector);
                solveEnergyEquation(ballPair);
            }

        }
    }

    private void wallCollision(Ball wall, Ball ball) {
        velocitySolver.wallCollision(wall,ball);
    }

    private void solveEnergyEquation(Pair<Ball> ballPair) {
        //System.out.println("solving energy Equation for ballPair: "+ballPair);
        velocitySolver.solveVelocities(ballPair);
    }

    private void correctPosition(Pair<Ball> ballPair, Vector2D collisionPoint, Vector2D distanceVector) {
        Vector2D ballPosition = new Vector2D(collisionPoint);
        Vector2D shiftPosition = new Vector2D(distanceVector);

        ballPosition.addVector2D(Vector2DMath.multiplyVector2D(shiftPosition, -1 * ballPair.getFirst().getRadius()));
        if(!ballPair.getFirst().isWall()) {
            ballPair.getFirst().setPosition(ballPosition);
        }
        
        ballPosition.equalsVector2D(collisionPoint);
        ballPosition.addVector2D(Vector2DMath.multiplyVector2D(shiftPosition,1*ballPair.getSecond().getRadius()));
        
        if(!ballPair.getSecond().isWall()) {
            ballPair.getSecond().setPosition(ballPosition);
        }

    }

    private Vector2D calculateCollisionPoint(Ball ball1, Ball ball2){
        Vector2D collisionPoint =new Vector2D(ball1.getPosition());
        collisionPoint.multiply(ball1.getRadius());
        Vector2D v2 = new Vector2D(ball2.getPosition());
        v2.multiply(ball2.getRadius());

        collisionPoint.addVector2D(v2);
        collisionPoint.multiply((double)1/(ball1.getRadius()+ball2.getRadius()));
        return collisionPoint;

    }

    public void dynamicCollision() {

        Vector2D collisionPoint;
        Vector2D closestPoint;
        Vector2D newPosition;
        Vector2D shiftVector;
        Vector2D endPoint;
        Vector2D relativePosition;
        Vector2D relativeVelocity;
        Ball staticBall=new Ball();
        Ball dynamicBall = new Ball();
        double distanceSquare=0;
        double radiiSquare = 0 ;
        double distanceToClosestPoint = 0 ;
        double shift = 0 ;
        double maxDistanceSquare = 0 ;


        for (Pair<Ball> ballPair:collisionPairs) {

            //zmiana ukladu odniesienia
            System.out.println("przed zmianą : "+ballPair);
            staticBall = ballPair.getFirst();
            dynamicBall = ballPair.getSecond();
            relativePosition = new Vector2D(staticBall.getPosition());
            relativeVelocity = new Vector2D(staticBall.getVelocity());

            relativeShift(dynamicBall,relativePosition,relativeVelocity);
            if(dynamicBall.getVelocity().getLengthSquare()==0){
                relativeShift(dynamicBall,relativePosition.multiply(-1),relativeVelocity.multiply(-1));
                continue;
            }
            System.out.println("po zmianie : "+ballPair);

            System.out.println("dynamic ball velocity : "+dynamicBall.getVelocity());
            System.out.println("deltatime : "+deltaTime);
            endPoint = calculateEndPoint( dynamicBall);
            System.out.println("endPoint : "+endPoint);
            closestPoint=calculateDynamicCollisionClosestPoint(staticBall,dynamicBall,endPoint);
            System.out.println("closedPoint : "+closestPoint);
            distanceSquare = Vector2DMath.distanceSquared(closestPoint,staticBall.getPosition());
            System.out.println("distanceSquare : " + distanceSquare);
            radiiSquare=(staticBall.getRadius() + dynamicBall.getRadius())*(staticBall.getRadius() + dynamicBall.getRadius());
            maxDistanceSquare = Vector2DMath.vector2DSubtract(endPoint,dynamicBall.getPosition()).getLengthSquare();
            distanceToClosestPoint = Vector2DMath.vector2DSubtract(dynamicBall.getPosition(),closestPoint).getLengthSquare();

            System.out.println("Distance to closest point : "+ distanceToClosestPoint);
            System.out.println("MaxDistance : "+ maxDistanceSquare);

            //Nie będzie zderzenia
            if(distanceSquare > radiiSquare && maxDistanceSquare<distanceToClosestPoint){
                relativeShift(dynamicBall,relativePosition.multiply(-1),relativeVelocity.multiply(-1));
                System.out.println("Za daleko nie ma zderzenia , powrot :"+ballPair);

                continue;
            }
            shift = Math.sqrt((staticBall.getRadius()+dynamicBall.getRadius())*(staticBall.getRadius()+dynamicBall.getRadius())-distanceSquare);
            shiftVector = new Vector2D(dynamicBall.getVelocity()).normalize().multiply(shift*(-1));

            //Nowa pozycja ruchomej kulki
            newPosition = Vector2DMath.vector2DSum(closestPoint,shiftVector);
            dynamicBall.setPosition(newPosition);


            //Powrot do normalnego ukladu odniesienia
            relativeShift(dynamicBall,relativePosition.multiply(-1),relativeVelocity.multiply(-1));

            //ustalenie predkosci
            System.out.println("Zmieniam predkosci");
            velocitySolver.solveVelocities(ballPair);

        }
    }

    private void relativeShift(Ball dynamicBall, Vector2D relativePosition, Vector2D relativeVelocity) {
        dynamicBall.shiftPosition(Vector2DMath.multiplyVector2D(relativePosition,-1));
        dynamicBall.shiftVelocity(Vector2DMath.multiplyVector2D(relativeVelocity,-1));
    }

    private Vector2D calculateEndPoint(Ball dynamicBall) {
        return new Vector2D(dynamicBall.getPosition()).addVector2D(Vector2DMath.multiplyVector2D(dynamicBall.getVelocity(),deltaTime));
    }

    private Vector2D calculateDynamicCollisionClosestPoint(Ball staticBall, Ball dynamicBall, Vector2D endPoint) {
        Vector2D closestPoint;
        System.out.println("p1 : "+ dynamicBall.getPosition()+" p2 : "+endPoint);
        Line l1 = new Line(dynamicBall.getPosition(),endPoint);
        closestPoint = l1.getClosestPointOnLine(staticBall.getPosition());
        return closestPoint;
    }


}
