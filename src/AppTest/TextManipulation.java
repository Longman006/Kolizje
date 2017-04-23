package AppTest;/**
 * Created by longman on 23.04.17.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class TextManipulation extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text");
        Group root = new Group();
        Scene scene = new Scene(root,1200,600);

        Random random = new Random(System.currentTimeMillis());

        Text text;
        int nTexts = 100;
        for(int i =0 ; i< nTexts ; i++ ){
            root.getChildren().add(generateText(random,(int)scene.getWidth(),(int)scene.getWidth()));
        }

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(12);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.BLACK);
        dropShadow.setBlurType(BlurType.GAUSSIAN);

        text = new Text();
        text.setEffect(dropShadow);
        text.setText("DropShadow");
        text.setFill(Color.RED);
        text.setFont(Font.font("Serif",60));
        text.setX(scene.getWidth()/8);
        text.setY(scene.getHeight()/2);

        root.getChildren().add(text);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Text generateText(Random random, int width, int height){
        Text text = new Text(
                random.nextInt(width),
                random.nextInt(height),
                generateString(random)
        );
        text.setFont(Font.font("Serif",random.nextInt(50)));
        double opacity = 0.4;
        text.setFill(Color.rgb(
                random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255),
                opacity
                ));
        return text;
    }
    private String generateString(Random random){
        int length = 10;
        char[] ch = new char[length];
        for(int i = 0 ; i<length ; i++) {
            ch[i] = (char)(random.nextInt(255));
        }
        return new String(ch);
    }
}
