package Model.Simbolos.Funciones;

import Model.Simbolos.Exp;

public class Opc_Repeat extends Exp {
	
	public Exp ops[]=new Exp[2];
	
	public Opc_Repeat() {
		this.putTam(2); 				// Numero de hijos 
		this.putOperacion("Repeat"); 	// Nombre de la operacion
		this.ops[0]=null; 				// Hijo 1
		this.ops[1]=null;				// Hijo 2
	}
	
	public Opc_Repeat(Exp ops[]) {
		this.putTam(2); 				// Numero de hijos
		this.putOperacion("Repeat"); 	// Nombre de la operacion
		this.ops[0]=ops[0];				// Hijo 1
		this.ops[1]=ops[1];				// Hijo 2
		this.putX(this.ops[0].getX());	// Casilla X
		this.putY(this.ops[0].getY());	// Casilla Y
	}
	
	
	public String toString() {
	   return "REPEAT(("+ops[0]+"), ("+ops[1]+"))";
    }

	@Override
	public void setHijo(int i, Exp hijo) { // A�ade hijo
		this.ops[i]=hijo;
		if(i==0) {
			this.putX(hijo.getX());
			this.putY(hijo.getY());
		}
	}

	@Override
	public Exp getHijo(int i) {		
		return ops[i];
	}

	@Override
	public Exp duplica() {
		return new Opc_Repeat();
	}


}
