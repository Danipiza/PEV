package Model.Simbolos.Terminales;

import Model.Simbolos.Exp;

public class Opc_Mueve_Primer extends Exp {

	public Opc_Mueve_Primer() {
		this.putTam(0); 				// Numero de hijos
		this.putOperacion("Mueve_Primer"); 	// Nombre de la operacion
		this.putX(0);
		this.putY(0);
	}	
	
		
	public String toString() {
	   return "MUEVE_PRIMER";
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
		return new Opc_Mueve_Primer();
	}
}
