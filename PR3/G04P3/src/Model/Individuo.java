package Model;

import java.util.List;

import Model.Simbolos.Exp;

public abstract class Individuo {

	public double fitness;
	// A: avanza, I: izquierda, SXY: salto x y
	
	public List<String> operaciones;
	public int nodos;
	public boolean[] opcs;
	public int numOPopc;

}
