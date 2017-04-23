package colorfulcircles;/**
 * Created by longman on 23.04.17.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Circles extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        double orbitRadius = 100;
        double omega = 0.5;
        double height=600;
        double width = 400;
        Group root = new Group();
        Scene scene = new Scene(root,1200,800);
        Canvas primaryCanvas = new Canvas(width,height);

        primaryStage.setScene(scene);
        root.getChildren().add(primaryCanvas);

        GraphicsContext gc = primaryCanvas.getGraphicsContext2D();

        Circle center = new Circle(10, Color.RED);
        Circle orbit = new Circle(5, Color.RED);
        Rectangle background = new Rectangle(width,height);

        center.setCenterX(250);
        center.setCenterY(250);



        final long startNanoTime = System.nanoTime();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long nowNanoTime) {
                double t = (nowNanoTime-startNanoTime)/1000000000;
                double x = center.getCenterX()+center.getRadius()+orbitRadius*Math.cos(omega*t);
                double y = center.getCenterY()+center.getRadius()+orbitRadius*Math.sin(omega*t);

                gc.setFill(Color.BLACK);
                gc.fillRect(background.getX(),background.getY(),background.getWidth(),background.getHeight());
                gc.setFill(Color.RED);
                gc.fillOval(x,y,orbit.getRadius(),orbit.getRadius());
                gc.fillOval(center.getCenterX(),center.getCenterY(),center.getRadius(),center.getRadius());
            }
        };

        animationTimer.start();
        primaryStage.show();
    }
}
