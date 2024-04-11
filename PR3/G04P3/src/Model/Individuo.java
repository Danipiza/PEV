package Model;

import java.util.ArrayList;
import java.util.List;

import Model.Simbolos.Exp;

public class Individuo {
	
	public Arbol gen;
	public double fitness;
	// A: avanza, I: izquierda, SXY: salto x y
	public List<String> operaciones;
	public int tamFunciones;
	public int tamTerminales;
	
	public Individuo(int modo, int profundidad) {
		this.tamTerminales=0;
		this.gen=new Arbol(modo, profundidad);
		this.fitness=0;
		operaciones=new ArrayList<String>();
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
		int hijos=nodo.getTam();		
		for(int i=0;i<hijos;i++) {					
			recorreArbol(nodo.getHijo(i));
		}	
		
		if(nodo.getOperacion().equals("Avanza")) operaciones.add("A");
		if(nodo.getOperacion().equals("Izquierda")) operaciones.add("I");
		else if(nodo.getOperacion().equals("Salta")) operaciones.add("S"+nodo.getX()+nodo.getY());
		if(hijos==0) this.tamTerminales++;	
		else this.tamFunciones++;
	}
	
	public void printIndividuo() {
		System.out.println(gen);
	}
	
	public String toString() {
		return ""+gen;
	}
	
}





