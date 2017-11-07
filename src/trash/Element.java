package trash;

import javafx.geometry.BoundingBox;
import pl.edu.pw.fi.szypula.tomasz.Vector2D;

/**
 * Created by longman on 17.04.17.
 */
public interface Element {
    double getX();
    double getY();
    BoundingBox getBoundingBox();
    Vector2D getPosition();
}
