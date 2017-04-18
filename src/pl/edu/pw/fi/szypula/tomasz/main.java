package pl.edu.pw.fi.szypula.tomasz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main {

    public static void maipran(String[] args) {

        final int xmax = 100;
        final int ymax = 100;
        final double gridCellSize = 2;
        Box2D bounds = new Box2D(new Vector2D(), new Vector2D(xmax, ymax));
        List<Circle> circles = new ArrayList<Circle>();
        double x, y;
        double r = 10;
        Circle c;
        Vector2D point, velocity;
        for (int i = 0; i < 10; i++) {
            x = Math.random();
            y = Math.random();
            point = new Vector2D(x * xmax, y * ymax);
            velocity = new Vector2D(x, y);
            c = new Circle(point, r, velocity);
            System.out.println("Adding circle :" + c.toString());
            circles.add(c);
        }
        Grid grid = new Grid(bounds, gridCellSize);
        grid.fillGrid(circles);


    }

}
