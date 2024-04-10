package Model.Simbolos.Funciones;

import Model.Simbolos.Exp;

public class Salta extends Exp {
	
	public Exp v;
	
	public Salta(Exp v) {
		this.v=v;
	}
	
	@Override
	public int getX() {
		return v.getX();
	}

	@Override
	public int getY() {
		return v.getY();
	}

	public String toString() {
	   return "SUMA("+v+")";
    }

}
