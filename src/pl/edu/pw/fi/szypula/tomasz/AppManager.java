package pl.edu.pw.fi.szypula.tomasz;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import javafx.application.Application;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by longman on 07.05.17.
 */
public class AppManager extends Application{

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        GraphicsManager graphicsManager = new GraphicsManager();
        double deltaTime =1;
        double maxDeltaPosition = 60;
        ModelManager modelManager = new ModelManager(new Vector2D(),new Vector2D(600,720),maxDeltaPosition,deltaTime);
        List<Drawable> drawables = new ArrayList<>();

        /**
         * Test
         */
        Line lineTest = new Line(new Vector2D(1,3),new Vector2D(2,4));
        Vector2D closestPointOnLine = lineTest.getClosestPointOnLine(new Vector2D(-2,5));
        System.out.println("Line test : " + closestPointOnLine);

        drawables.add(modelManager.getGrid());
        drawables.addAll(modelManager.getBallList());

        graphicsManager.addAll(drawables);
        graphicsManager.draw();

        for(Pair<Ball> ballPair : modelManager.getCollisionPairs() ){
            drawPair(ballPair, graphicsManager.getGraphicsContext());
        }

        primaryStage.setScene(graphicsManager.getPrimaryScene());
        primaryStage.show();


        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // Here comes your void to refresh the whole application.
                graphicsManager.draw();
                for(Pair<Ball> ballPair:modelManager.getCollisionPairs()){
                    drawPair(ballPair,graphicsManager.getGraphicsContext());
                }
                modelManager.createCollisionPairs();
                modelManager.staticCollision();
               // modelManager.dynamicCollsion();
                modelManager.updatePosition(deltaTime);



            }
        }, 100, 100);
    }
    private void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private static void drawPair(Pair<Ball> ballPair, GraphicsContext graphicsContext){
            graphicsContext.setStroke(Color.BLUE);
            graphicsContext.strokeLine(ballPair.getFirst().getX(),ballPair.getFirst().getY(),ballPair.getSecond().getX(),ballPair.getSecond().getY());
        }
    }


