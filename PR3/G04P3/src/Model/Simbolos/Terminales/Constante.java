package Model.Simbolos.Terminales;

import java.util.Random;

import Model.Simbolos.Exp;

public class Constante extends Exp {
	
	public Constante() {
		Random random = new Random();        
		this.putX(random.nextInt(8));
		this.putY(random.nextInt(8));
	}
	
	@Override
	public int getX() {
		return this.getX();
	}

	@Override
	public int getY() {
		return this.getY();
	}
	
	public String toString() {
	   return this.getX()+","+this.getY();
    }
	
}
