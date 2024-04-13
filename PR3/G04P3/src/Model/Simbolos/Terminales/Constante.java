package Model.Simbolos.Terminales;

import java.util.Random;

import Model.Simbolos.Exp;

public class Constante extends Exp {
	
	public Constante(int filas, int columnas) {
		this.putTam(0); 				// Numero de hijos
		this.putOperacion("Constante"); // Nombre de la operacion
		Random random = new Random();    
		this.putX(random.nextInt(filas));
		this.putY(random.nextInt(columnas));

	}

	public Constante(Constante con) {
		this.putTam(0); 				// Numero de hijos
		this.putOperacion("Constante"); // Nombre de la operacion 
		this.putX(con.getX());
		this.putY(con.getY());
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

	@Override
	public Exp duplica() {
		return new Constante(this);
	}
	
}
