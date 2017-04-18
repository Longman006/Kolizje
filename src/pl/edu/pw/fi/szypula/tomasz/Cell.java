/**
 *
 */
package pl.edu.pw.fi.szypula.tomasz;

import java.util.ArrayList;
import java.util.List;

/**
 * @author longman
 */
public class Cell {

    private List<Element> circles = new ArrayList<>();
    private List<Cell> cells = new ArrayList<>();
    boolean isWall;

    public boolean isWall() {
        return isWall;
    }

    public Cell() {
        isWall = false;

    }

    public void insertElement(Element element) {
        circles.add(element);
    }

    public List<Element> getElements() {
        return circles;
    }

    public void addNeighbour(Cell c) {
        cells.add(c);
    }


}
