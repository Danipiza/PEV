package Model.Simbolos.Funciones;

import Model.Simbolos.Exp;

public class Salta extends Exp {
	
	public Exp ops[]=new Exp[1];
	
	public Salta() {
		this.putTam(1); 				// Numero de hijos
		this.putOperacion("Salta"); 	// Nombre de la operacion
		this.ops[0]=null;				// Unico Hijo
	}
	
	public Salta(Exp ops[]) {
		this.putTam(1); 				// Numero de hijos
		this.putOperacion("Salta"); 	// Nombre de la operacion
		this.ops[0]=ops[0];				// Unico Hijo
		this.putX(this.ops[0].getX());
		this.putY(this.ops[0].getY());
	}
	
	
	public String toString() {
	   return "SALTA("+ops[0]+")";
    }
	
	@Override
	public void setHijo(int i, Exp hijo) { // Añade Hijo
		this.ops[i]=hijo;	
		this.putX(hijo.getX());
		this.putY(hijo.getY());
	}
	
	@Override
	public Exp getHijo(int i) {		
		return ops[i];
	}

}
