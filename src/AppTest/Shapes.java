package AppTest;/**
 * Created by longman on 23.04.17.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import sun.nio.ch.sctp.SctpNet;

public class Shapes extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Rectangle rectangle = new Rectangle(500,100,200,100);
        Rectangle origin = new Rectangle(rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());
        rectangle.setFill(Color.RED);
        rectangle.setStroke(Color.BLACK);

        Rotate rotate = new Rotate();
        rotate.setAngle(30);
        rotate.setPivotX(0);
        rotate.setPivotY(0);

        Scale scale = new Scale();
        scale.setX(0.8);
        scale.setY(0.5);

        Translate translate = new Translate();
        translate.setX(500);
        translate.setY(100);

        rectangle.getTransforms().addAll(translate,rotate,scale);

        Group root = new Group();
        root.getChildren().addAll(origin,rectangle);

        Scene scene = new Scene(root,1200,800);

        primaryStage.setTitle("Rectangle");
        primaryStage.setScene(scene);
        primaryStage.show();

        }
}
