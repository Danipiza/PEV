package Model.Simbolos.Terminales;

import Model.Simbolos.Exp;

public class Opc_Retrocede extends Exp{
	public Opc_Retrocede() {
		this.putTam(0); 				// Numero de hijos
		this.putOperacion("Retrocede"); 	// Nombre de la operacion
		this.putX(0);
		this.putY(0);
	}	
	
		
	public String toString() {
	   return "RETROCEDE";
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
		return new Opc_Retrocede();
	}
}
