package Model.Simbolos.Funciones;

import Model.Simbolos.Exp;

public class Suma extends Exp{
	
	public Exp v1;
	public Exp v2;
	
	public Suma(Exp v1, Exp v2) {
		this.v1=v1;
		this.v2=v2;
	}
	
	@Override
	public int getX() {
		return v1.getX()+v2.getX();
	}

	@Override
	public int getY() {
		return v1.getY()+v2.getY();
	}
	
	public String toString() {
	   return "SUMA(("+v1+"), ("+v2+"))";
    }

}
