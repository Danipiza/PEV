package Model.Simbolos;

public abstract class Exp {

	private int x;
	private int y;	
	private int tam;
	private String operacion;
	
	
	public Exp() {}
	
	
	public abstract void setHijo(int i, Exp hijo);
	public abstract Exp getHijo(int i);
	
	public void putX(int x) { this.x=x; }
	public int getX() { return this.x; }
	
	public void putY(int y) { this.y=y; }
	public int getY() { return this.y; }
	
	public void putTam(int tam) { this.tam=tam; }
	public int getTam() { return this.tam; }
	
	public void putOperacion(String op) { this.operacion=op; }
	public String getOperacion() { return this.operacion; }



	public String getOp() {
		if (this.operacion.equals("Salta")) {
			return String.valueOf(this.operacion.charAt(0)) + x + y;
		} else {
			return String.valueOf(this.operacion.charAt(0));
		}
	}
	
	

    public abstract Exp duplica();

}
