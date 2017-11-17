package pl.edu.pw.fi.szypula.tomasz;

import javafx.geometry.BoundingBox;

/**
 * Created by longman on 11.06.17.
 */
public class EnergyMomentumConservationSolver {
    public EnergyMomentumConservationSolver() {
        }

    public void solveVelocities(Pair<Ball> ballPair) {

        Ball ball1 = ballPair.getFirst();
        Ball ball2 = ballPair.getSecond();
        Vector2D nnn = new Vector2D(ball2.getX()-ball1.getX(),ball2.getY() - ball1.getY());
        nnn.normalize();
        Vector2D sss = new Vector2D(-nnn.getY(),nnn.getX());
        //System.out.println("Collision:");
       // System.out.println("Normal :"+nnn);
       // System.out.println("TransVersal "+sss);
       // System.out.println("Ball1 : "+ball1);
        //System.out.println("Ball2 :" +ball2);

        double v1n = Vector2DMath.scalarProduct(nnn,ball1.getVelocity());
        double v2n = Vector2DMath.scalarProduct(nnn,ball2.getVelocity());
        double v1s = Vector2DMath.scalarProduct(sss,ball1.getVelocity());
        double v2s = Vector2DMath.scalarProduct(sss,ball2.getVelocity());
        double m1 = ball1.getMass();
        double m2 = ball2.getMass();
        double v1nPrime = (v1n*(m1-m2)+2*m2*v2n)/(m1+m2);
        double v2nPrime = (v2n*(m2-m1)+2*m1*v1n)/(m1+m2);

       // System.out.println("v1n : "+v1n);
        //System.out.println("v2n : "+v2n);
        //System.out.println("v1nPrime : "+v1nPrime);
        //System.out.println("v2nPrime : "+v2nPrime);

        Vector2D v1NormalPrime = Vector2DMath.multiplyVector2D(nnn,v1nPrime);
        Vector2D v1TransversalPrime = Vector2DMath.multiplyVector2D(sss,v1s);
        Vector2D v2NormalPrime = Vector2DMath.multiplyVector2D(nnn,v2nPrime);
        Vector2D v2TransversalPrime = Vector2DMath.multiplyVector2D(sss,v2s);

        Vector2D wypadkowy1 = Vector2DMath.vector2DSum(v1NormalPrime,v1TransversalPrime);
        Vector2D wypadkowy2 = Vector2DMath.vector2DSum(v2NormalPrime,v2TransversalPrime);

        if(ball1.isWall()){
            ball2.setVelocity(Vector2DMath.vector2DSubtract(wypadkowy1,wypadkowy2));
        }

        else if(ball2.isWall()){
            ball1.setVelocity(Vector2DMath.vector2DSubtract(wypadkowy2,wypadkowy1));
        }
        else {
            ball1.setVelocity(v1NormalPrime.addVector2D(v1TransversalPrime));
            ball2.setVelocity(v2NormalPrime.addVector2D(v2TransversalPrime));
        }


    }


    public void wallCollision(Ball wall, Ball ball) {
        double radius = ball.getRadius();
        double ballRadius = ball.getRadius();
        double wallRadius = wall.getRadius();
        BoundingBox wallBox = wall.getBoundingBox();
        double walllheight = wallBox.getHeight();
        double wallWidth = wallBox.getWidth();
        double deltaY,deltaX,bottomWall,topWall,leftWall,rightWall,bottomBall,topBall,leftBall,rightBall = 0 ;
        System.out.println("Wall Collision ");
        System.out.println("Wall : "+wall);
        System.out.println("ball : "+ball);
        //bottom (ball)
        deltaY=Math.abs(ball.getY()-wall.getY());
        deltaX = Math.abs(ball.getX()-wall.getX());

        bottomWall = wall.getBottom();
        topWall =wall.getTop();
        leftWall=wall.getLeft();
        rightWall=wall.getRight();

        bottomBall = ball.getBottom();
        topBall =ball.getTop();
        leftBall=ball.getLeft();
        rightBall=ball.getRight();


        Vector2D xAxis = new Vector2D(-1,1);
        Vector2D multiplier = new Vector2D(1,1);
        Vector2D yAxis = new Vector2D(1,-1);
        double deltaPrev = wall.getRadius() ;
        double delta = 0 ;

        //LEFT
        if((delta=Math.abs((ball.getX()+ballRadius)-(wall.getX()-wallRadius)))<deltaPrev && ball.getVelocity().getX()>0){
            deltaPrev = delta ;
            multiplier = xAxis;
            System.out.println("LEFT = "+delta);
        }
        //RIGHT
        if((delta=Math.abs((ball.getX()-ballRadius)-(wall.getX()+wallRadius)))<deltaPrev && ball.getVelocity().getX()<0){
            deltaPrev = delta ;
            multiplier = xAxis;
            System.out.println("RIGHT = "+delta);
        }
        //TOP
        if((delta=Math.abs((ball.getY()+ballRadius)-(wall.getY()-wallRadius)))<deltaPrev && ball.getVelocity().getY()>0){
            deltaPrev = delta ;
            multiplier = yAxis;
            System.out.println("TOP = "+delta);
        }
        //BOTTOM
        if((delta=Math.abs((ball.getY()-ballRadius)-(wall.getY()+wallRadius)))<deltaPrev && ball.getVelocity().getY()<0){
            deltaPrev = delta ;
            multiplier = yAxis;
            System.out.println("BOTTOM = "+delta);
        }
        ball.getVelocity().multiplyTomek(multiplier);

/**

        //deltaX=ball.getX()
        if(ball.getY()-radius<wallBox.getMinY()+walllheight && ball.getVelocity().getY()<0) {
            yAxisCollision(ball);
            System.out.println("bottom");
        } //top
        else if(ball.getY()+radius>wallBox.getMinY() && ball.getVelocity().getY()>0) {
            yAxisCollision(ball);
            System.out.println("top");
        }

                //left wall
        else if(ball.getX()-radius<wallBox.getMinX()+wallWidth && ball.getVelocity().getX()<0) {
            xAxisCollision(ball);
            System.out.println("right");
        }


        //right
        else if(ball.getX()+radius>wallBox.getMinX() && ball.getVelocity().getX()>0) {
            xAxisCollision(ball);
            System.out.println("left");
        }
 **/

    }

    private void yAxisCollision(Ball ball) {
        ball.getVelocity().setY(-ball.getVelocity().getY());
    }

    private void xAxisCollision(Ball ball) {
        ball.getVelocity().setX(-ball.getVelocity().getX());
    }
}
