package pl.edu.pw.fi.szypula.tomasz;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;



public class Grid implements Drawable {

	private Cell[][] cells;
	private List<Cell> cellsList = new ArrayList<Cell>();
	private double gridCellSize = 0;
	private int columns,rows=0;
	private BoundingBox boundingBox;

	public final Cell[][] getCellsArray() {
		return cells;
	}

	public List<Cell> getCellsList() {
		return cellsList;
	}

	public List<Cell>getBorderCells(){
		List<Cell> borderCells = new ArrayList<>();
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				if(i==0 || j == 0 || i == columns-1|| j == rows-1){
					borderCells.add(cells[i][j]);
				}
			}
		}
		return borderCells;
	}
	public Grid(BoundingBox boundingBox, double gridCellSize){
		this.boundingBox = boundingBox;
		this.gridCellSize=gridCellSize;
		columns = (int)Math.ceil(boundingBox.getWidth()/gridCellSize);
		rows = (int)Math.ceil(boundingBox.getHeight()/gridCellSize);
		System.out.println(this.toString());
        createCells();
		fillNeighbours();
		
	}
	private void createCells(){
        boolean isWall = false;
        cells = new Cell[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j] = new Cell(isWall, new BoundingBox(i*gridCellSize,j*gridCellSize,gridCellSize,gridCellSize));
                cellsList.add(cells[i][j]);
            }
        }
    }
	public void fillGrid(List<Ball> balls){
		clearGrid();
		for(Ball c : balls){
			this.insertElement(c);
		}
	}

	private void clearGrid() {
		for (Cell cell:
			 cellsList) {
			cell.clear();
		}
	}

	private void insertElement(Ball ball){
		int columnCenter = (int)((ball.getX() - boundingBox.getMinX())/gridCellSize);
		int rowCenter = (int)((ball.getY() - boundingBox.getMinY())/gridCellSize);
		int row,column;
		int span = (int)(ball.getRadius()/gridCellSize);

		//System.out.println("Center col : "+columnCenter+" row : "+rowCenter);
		if(span==0){
			cells[columnCenter][rowCenter].insertElement(ball);
		}
		for(int i = -span ; i<span ; i++){
			row = i + rowCenter;
			for(int j = -span ; j<span ; j++){
				column = j + columnCenter;
				//System.out.println("Inserting col : "+column+" row : "+row);
				if(boundingBox.contains(column*gridCellSize,row*gridCellSize))
				cells[column][row].insertElement(ball);
			}
		}

		
	}
	private void fillNeighbours(){
		BoundingBox bounds = new BoundingBox(0,0,columns-1,rows-1);
		int iNeighbour=0;
		int jNeighbour=0;
		for(int i = 0 ; i<columns ; i++){
			for(int j =0 ; j<rows ; j++){
				System.out.println("Cell i = "+i+"  j= "+j);
				for(int plusI = -1 ; plusI<2 ; plusI++){
					for(int plusJ = -1 ; plusJ<2 ;plusJ++){
						if(plusI!=0 || plusJ!=0){
							iNeighbour = i+plusI;
							jNeighbour = j + plusJ;
							if(bounds.contains(iNeighbour,jNeighbour)){
								System.out.println("Neighbour i = "+iNeighbour+"  j= "+jNeighbour);
								cells[i][j].addNeighbour(cells[iNeighbour][jNeighbour]);
							}
						}

					}
				}
			}
		}
	}
	@Override
	public String toString() {
		return "Grid{" +
				"gridCellSize=" + gridCellSize +
				", columns=" + columns +
				", rows=" + rows +
				", boundingBox=" + boundingBox +
				'}';
	}

	@Override
	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.setStroke(Color.BLACK);
		for(Cell cell:cellsList){
			cell.draw(graphicsContext);
		}
		for(int i = 0 ; i<= rows ;i++){
			graphicsContext.strokeLine(boundingBox.getMinX(),i*gridCellSize,boundingBox.getMaxX(),i*gridCellSize);

		}
		for(int j = 0 ; j<= columns ;j++){
			graphicsContext.strokeLine(j*gridCellSize,boundingBox.getMinY(),j*gridCellSize,boundingBox.getMaxY());
		}
	}
}
