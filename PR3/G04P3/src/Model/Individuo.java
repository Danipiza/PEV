package Model;

import java.util.ArrayList;
import java.util.List;

import Model.Simbolos.Exp;
import Model.Simbolos.Funciones.Salta;
import Utils.Pair;

public class Individuo {
	
	public Arbol gen;
	public double fitness;
	// A: avanza, I: izquierda, SXY: salto x y
	public List<String> operaciones;
	public List<Pair<Exp,Integer>> funcionales;
	public List<Pair<Exp,Integer>> terminales;
	
	public Individuo(int modo, int profundidad) {
		this.gen=new Arbol(modo, profundidad);
		this.fitness=0;
		operaciones=new ArrayList<String>();

		funcionales = new ArrayList<Pair<Exp,Integer>>();
		Exp invisible = new Salta();
		invisible.setHijo(0, gen.raiz);
		funcionales.add(new Pair<>(invisible, 0));
		terminales = new ArrayList<Pair<Exp,Integer>>();

		recorreArbol(gen.raiz);
	}
	
	public Individuo(Individuo individuo) {	
		// TODO cambiar para copiar los nodos uno a uno		
		this.gen=individuo.gen;//new Arbol(individuo.gen);
		this.fitness=0;
		operaciones=new ArrayList<String>();
		recorreArbol(gen.raiz);
	}
	
	public void recorreArbol(Exp nodo) {		
		for(int i=0;i<nodo.getTam();i++) {				
			recorreArbol(nodo.getHijo(i));
			if(nodo.getHijo(i).getTam() == 0) terminales.add(new Pair<>(nodo.getHijo(i), i));
			else funcionales.add(new Pair<>(nodo.getHijo(i), i));	
		}	
		
		operaciones.add(nodo.getOp());
		
	}
	
	public void printIndividuo() {
		System.out.println(gen);
	}
	
	public String toString() {
		return ""+gen;
	}
	
}





