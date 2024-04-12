package Model.Simbolos.Terminales;

import Model.Simbolos.Exp;

public class Avanza extends Exp{

	public Avanza() {
		this.putTam(0); 				// Numero de hijos
		this.putOperacion("Avanza"); 	// Nombre de la operacion
		this.putX(0);
		this.putY(0);
	}	
	
		
	public String toString() {
	   return "AVANZA";
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
		return new Avanza();
	}

}
