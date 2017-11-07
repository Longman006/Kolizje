package pl.edu.pw.fi.szypula.tomasz;

import javafx.application.Application;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by longman on 07.05.17.
 */
public class GraphicsManager {
    private Rectangle2D boundingBox;
    private Group root;
    private Canvas primaryCanvas;
    private GraphicsContext graphicsContext;
    private Scene primaryScene;
    private List<Drawable> drawList;

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Scene getPrimaryScene() {

        return primaryScene;
    }

    public GraphicsManager() {
        Rectangle2D boundingBox = Screen.getPrimary().getVisualBounds();
        this.boundingBox = boundingBox;
        primaryCanvas = new Canvas(boundingBox.getWidth(),boundingBox.getHeight());
        root = new Group();
        graphicsContext=primaryCanvas.getGraphicsContext2D();
        addNode(primaryCanvas);
        primaryScene = new Scene(root,boundingBox.getWidth(),boundingBox.getHeight());
        drawList = new ArrayList<>();
    }
    public void addNode(Node node){
        root.getChildren().add(node);
    }
    public void addDrawable(Drawable drawable){
        drawList.add(drawable);
    }
    public void addAll(List<Drawable> drawables){
        drawList.addAll(drawables);
    }

    public void draw(){
        clearScreen();
        for(Drawable toDraw : drawList){
            toDraw.draw(graphicsContext);
        }
    }

    public void clearScreen() {
        graphicsContext.clearRect(0,0,boundingBox.getWidth(),boundingBox.getHeight());
    }
}
