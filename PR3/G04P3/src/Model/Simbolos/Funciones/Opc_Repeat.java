package Model.Simbolos.Funciones;

import java.util.Random;

import Model.Simbolos.Exp;

public class Opc_Repeat extends Exp {
	
	public Exp ops[]=new Exp[1];
	
	public Opc_Repeat() {
		this.putTam(1); 				// Numero de hijos 
		this.putOperacion("Repeat"); 	// Nombre de la operacion
		this.ops[0]=null; 				// Hijo 1
		Random random=new Random();
		this.putRepeat(random.nextInt(5)+1);
	}
	
	public Opc_Repeat(Exp ops[], int repeat) {
		this.putTam(1); 				// Numero de hijos
		this.putOperacion("Repeat"); 	// Nombre de la operacion
		this.ops[0]=ops[0];				// Hijo 1
		this.putRepeat(repeat);
		this.putX(this.ops[0].getX());	// Casilla X
		this.putY(this.ops[0].getY());	// Casilla Y
	}
	
	
	public String toString() {
	   return "REPEAT(("+ops[0]+"), ("+this.getRepeat()+"))";
    }

	@Override
	public void setHijo(int i, Exp hijo) { // A�ade hijo
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
		return new Opc_Repeat();
	}


}
