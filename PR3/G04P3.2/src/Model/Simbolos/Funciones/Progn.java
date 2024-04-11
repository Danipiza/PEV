package Model.Simbolos.Funciones;

import Model.Simbolos.Exp;

public class Progn extends Exp {
	public Exp op1;
	public Exp op2;
	
	public Progn(Exp op1, Exp op2) {
		this.op1=op1;
		this.op2=op2;
	}
	
	@Override
	public int getX() {
		return op2.getX();
	}

	@Override
	public int getY() {
		return op2.getY();
	}
	
	public String toString() {
	   return "PROGN(("+op1+"), ("+op2+"))";
    }
}
