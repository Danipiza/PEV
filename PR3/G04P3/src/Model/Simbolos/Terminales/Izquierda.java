package Model.Simbolos.Terminales;

import Model.Simbolos.Exp;

public class Izquierda extends Exp {

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}
	
	
	public String toString() {
	   return "IZQUIERDA";
    }
	
}
