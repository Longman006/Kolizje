package AppTest;/**
 * Created by longman on 23.04.17.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloWorld extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        int width = 300;
        int height = 500;
        double x,y,startX,startY,endX,endY;
        double length = 150;
        Paint fillColor = Color.WHITE;
        Group root = new Group();
        Scene scene = new Scene(root,width,height,fillColor);

        x = scene.getWidth()/2;
        y = scene.getHeight()/2;
        startX = x-length;
        startY = y-length;
        endX = x + length;
        endY = y + length;
        String text = "HELLO WORLD";


        root.getChildren().add(new Text(x,y,text));
        root.getChildren().add(drawGrid(0, 0, 15, scene.getWidth(), scene.getHeight()));
        // root.getChildren().add(new Line(startX,y,endX,y));
       // root.getChildren().add(new Line(x,startY,x,endY));
        primaryStage.setTitle("Hello World");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public Group drawGrid(double startx,double starty,double gridCellSize,double width,double height){
        int rows =  (int)(height/gridCellSize);
        int columns = (int) (width/gridCellSize);
        Group group = new Group();

        for(int i = 0 ; i<columns ; i++){
            double x = startx+i*gridCellSize;
            group.getChildren().add(new Line(x,starty,x,starty+height));
        }

        for(int i = 0 ; i<rows ; i++){
            double y = starty+i*gridCellSize;
            group.getChildren().add(new Line(startx,y,startx+width,y));
        }
        return group;

    }

}
