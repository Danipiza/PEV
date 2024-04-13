package Model;

import java.util.ArrayList;
import java.util.List;

import Model.Simbolos.Exp;
import Model.Simbolos.Funciones.Salta;
import Model.Simbolos.Terminales.Avanza;
import Model.Simbolos.Terminales.Constante;
import Utils.Pair;

public class IndividuoArbol extends Individuo {
	
	public Arbol gen;
	//public double fitness;
	// A: avanza, I: izquierda, SXY: salto x y
	//public List<String> operaciones;
	public List<Pair<Exp,Integer>> funcionales;
	public List<Pair<Exp,Integer>> terminales;
	
	public IndividuoArbol(int modo, int profundidad, int filas, int columnas) {
		this.gen=new Arbol(modo, profundidad,filas, columnas);
		this.fitness=0;
		operaciones=new ArrayList<String>();

		funcionales = new ArrayList<Pair<Exp,Integer>>();
		Exp invisible = new Salta();
		invisible.setHijo(0, gen.raiz);
		funcionales.add(new Pair<>(invisible, 0));
		terminales = new ArrayList<Pair<Exp,Integer>>();

		recorreArbol(gen.raiz);
	}
	
	public IndividuoArbol(IndividuoArbol individuo) {	
		this.gen = new Arbol();
		gen.raiz = duplicaArbol(individuo.gen.raiz);
		this.fitness=0;

		funcionales = new ArrayList<Pair<Exp,Integer>>();
		Exp invisible = new Salta();
		invisible.setHijo(0, gen.raiz);
		funcionales.add(new Pair<>(invisible, 0));
		terminales = new ArrayList<Pair<Exp,Integer>>();

		operaciones=new ArrayList<String>();
		recorreArbol(gen.raiz);
	}
	
	private Exp duplicaArbol(Exp original) {
		Exp nuevo;
		nuevo = original.duplica();
		for (int i = 0; i < original.getTam(); i++) {
			nuevo.setHijo(i, duplicaArbol(original.getHijo(i)));
		}
		return nuevo;
	}

	public void reiniciaListas(Exp nodo) {
		funcionales = new ArrayList<Pair<Exp,Integer>>();
		Exp invisible = new Salta();
		invisible.setHijo(0, gen.raiz);
		funcionales.add(new Pair<>(invisible, 0));
		terminales = new ArrayList<Pair<Exp,Integer>>();

		recorreArbol(nodo);
	}

	public void recorreArbol(Exp nodo) {		
		for(int i=0;i<nodo.getTam();i++) {				
			recorreArbol(nodo.getHijo(i));
			if(nodo.getHijo(i).getTam() == 0) terminales.add(new Pair<>(nodo, i));
			else funcionales.add(new Pair<>(nodo, i));	
		}	
		
		if(nodo.getOperacion().equals("Avanza")) operaciones.add("A");
		else if(nodo.getOperacion().equals("Izquierda")) operaciones.add("I");
		else if(nodo.getOperacion().equals("Salta")) operaciones.add("S"+nodo.getX()+nodo.getY());
		
		// NO HAY QUE A�ADIR TODAS LOS NODOS, SOLO LOS QUE CONSUMEN TICKS
		// AVANZA IZQUIERDA Y SALTA
		//operaciones.add(nodo.getOp());  
		
	}
	
	public String toString() {
		return ""+gen;
	}
	
}




