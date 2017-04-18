package pl.edu.pw.fi.szypula.tomasz;

import java.util.List;

public class Grid {

	private Cell[][] cells;
	private double gridCellSize = 0;
	private int columns,rows=0;
	private Box2D bounds = new Box2D();
	
	
	public Grid(Box2D bounds,double gridCellSize){
		this.bounds=bounds;
		this.gridCellSize=gridCellSize;
		columns = (int)Math.ceil(bounds.getWidth()/gridCellSize);
		rows = (int)Math.ceil(bounds.getHeight()/gridCellSize);
		System.out.println(this.toString());
		cells = new Cell[columns][rows];
		for(Cell[] ccc : cells){
			for (int i = 0; i < ccc.length; i++) {
				ccc[i]=new Cell();
			}
		}
		
	}
	public void fillGrid(List<Circle> circles){
		for(Circle c : circles){
			this.insertElement(c);
		}
	}
	private void insertElement(Circle c){
		int column = (int)((c.getX() - bounds.getPointMin().getX())/gridCellSize);
		int row = (int)((c.getY() - bounds.getPointMin().getY())/gridCellSize);
		System.out.println("Inserting Circle col : "+column+" row : "+row); 
		cells[column][row].insertElement(c);
		
	}
	@Override
	public String toString(){
		return "Grid : "+bounds.toString()+" columns : "+columns+" rows : "+rows+" gridCellSize : "+gridCellSize;
	}
	
}
