package Model.Simbolos.Funciones;

import Model.Simbolos.Exp;

public class Suma extends Exp{
		
	public Exp ops[]=new Exp[2];
	
	public Suma() {
		this.putTam(2); 				// Numero de hijos
		this.putOperacion("Suma"); 		// Nombre de la operacion
		this.ops[0]=null;				// Hijo 1
		this.ops[1]=null;				// Hijo 2
	}
	
	public Suma(Exp ops[]) {
		this.putTam(2); 				// Numero de hijos
		this.putOperacion("Suma"); 	// Nombre de la operacion
		this.ops[0]=ops[0];
		this.ops[1]=ops[1];
		this.putX((this.ops[0].getX()+this.ops[1].getX())%8);
		this.putY((this.ops[0].getY()+this.ops[1].getY())%8);
	}
	
		
	public String toString() {
	   return "SUMA(("+ops[0]+"), ("+ops[1]+"))";
    }
	
	@Override
	public void setHijo(int i, Exp hijo) { // A�ade hijo
		this.ops[i]=hijo;	
		this.putX((this.getX()+hijo.getX())%8);
		this.putY((this.getY()+hijo.getY())%8);
	}
	
	@Override
	public Exp getHijo(int i) {		
		return ops[i];
	}

	@Override
	public Exp duplica() {
		return new Suma();
	}

}
