package Model.Simbolos.Funciones;

import Model.Simbolos.Exp;

public class Opc_Salto_Casilla extends Exp {
	
	public Exp ops[]=new Exp[1];
	
	public Opc_Salto_Casilla() {
		this.putTam(1); 				// Numero de hijos
		this.putOperacion("Salta_Casilla"); 	// Nombre de la operacion
		this.ops[0]=null;				// Unico Hijo
	}
	
	public Opc_Salto_Casilla(Exp ops[]) {
		this.putTam(1); 				// Numero de hijos
		this.putOperacion("Salta_Casilla"); 	// Nombre de la operacion
		this.ops[0]=ops[0];				// Unico Hijo
		this.putX(this.ops[0].getX());
		this.putY(this.ops[0].getY());
	}
	
	
	public String toString() {
	   return "SALTA_CASILLA("+ops[0]+")";
    }
	
	@Override
	public void setHijo(int i, Exp hijo) { // A�ade Hijo
		this.ops[i]=hijo;	
		this.putX(hijo.getX());
		this.putY(hijo.getY());
	}
	
	@Override
	public Exp getHijo(int i) {		
		return ops[i];
	}

	@Override
	public Exp duplica() {
		return new Opc_Salto_Casilla();
	}


}
