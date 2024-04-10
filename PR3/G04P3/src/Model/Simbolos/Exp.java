package Model.Simbolos;

public abstract class Exp {

	private int x;
	private int y;	
	
	public abstract int getX();
	public abstract int getY();
	
	public void putX(int x) { this.x=x; }
	
	public void putY(int y) { this.y=y; }
}
