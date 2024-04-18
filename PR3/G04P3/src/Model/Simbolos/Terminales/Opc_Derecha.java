package Model.Simbolos.Terminales;

import Model.Simbolos.Exp;

public class Opc_Derecha extends Exp{
	
	public Opc_Derecha() {
		this.putTam(0); 				// Numero de hijos
		this.putOperacion("Derecha"); 	// Nombre de la operacion
		this.putX(0);
		this.putY(0);
	}	
	
		
	public String toString() {
	   return "DERECHA";
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
		return new Opc_Derecha();
	}
}
