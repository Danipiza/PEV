package Model.Simbolos.Terminales;

import Model.Simbolos.Exp;

public class Izquierda extends Exp {

	
	public Izquierda() {
		this.putTam(0); 					// Numero de hijos
		this.putOperacion("Izquierda"); 	// Nombre de la operacion
		this.putX(0);
		this.putY(0);
	}	
	
	
	public String toString() {
	   return "IZQUIERDA";
    }
	
	@Override
	public void setHijo(int i, Exp hijo) {		
	}
	@Override
	public Exp getHijo(int i) {	
		return null;
	}

	@Override
	public Exp duplica() {
		return new Izquierda();
	}
	
	
}
