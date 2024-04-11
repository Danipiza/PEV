package Model.Simbolos.Terminales;

import java.util.Random;

import Model.Simbolos.Exp;

public class Constante extends Exp {
	
	public Constante() {
		this.putTam(0); 				// Numero de hijos
		this.putOperacion("Constante"); // Nombre de la operacion
		Random random = new Random();        
		this.putX(random.nextInt(8));
		this.putY(random.nextInt(8));
	}
	
		
	public String toString() {
	   return this.getX()+","+this.getY();
    }
	
	@Override
	public void setHijo(int i, Exp hijo) {		
	}
	@Override
	public Exp getHijo(int i) {	
		return null;
	}
	
}
