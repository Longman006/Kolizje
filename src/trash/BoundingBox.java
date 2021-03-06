package trash;

import pl.edu.pw.fi.szypula.tomasz.Vector2D;

public class BoundingBox {
	private Vector2D pointMax;
	private Vector2D pointMin;
	public Vector2D getPointMin() {
		return pointMin;
	}
	public void setPointMin(Vector2D pointMin) {
		this.pointMin = pointMin;
	}
	public Vector2D getPointMax() {
		return pointMax;
	}
	public void setPointMax(Vector2D pointMax) {
		this.pointMax = pointMax;
	}
	
	
	BoundingBox(Vector2D pointMin, Vector2D pointMax){
		this.pointMin = pointMin;
		this.pointMax = pointMax;
	}
	public BoundingBox() {
		pointMax=new Vector2D();
		pointMin = new Vector2D();
	}
	public double getWidth(){
		return Math.abs(pointMax.getX()- pointMin.getX());
	}
	public double getHeight(){
		return Math.abs(pointMax.getY()- pointMin.getY());
	}
	@Override
	public String toString(){
		return "Box Min : "+pointMin.toString()+" Max : "+pointMax.toString();
	}

}
