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
	
	private int a;
	
	public IndividuoArbol(int modo, int profundidad, int filas, int columnas, 
			boolean[] opcs, int numOPopcF, int numOPopcT) {
		this.gen=new Arbol(modo, profundidad,filas, columnas, opcs, numOPopcF,numOPopcT);
		this.fitness=0;
		operaciones=new ArrayList<String>();

		funcionales = new ArrayList<Pair<Exp,Integer>>();
		Exp invisible = new Salta();
		invisible.setHijo(0, gen.raiz);
		funcionales.add(new Pair<>(invisible, 0));
		terminales = new ArrayList<Pair<Exp,Integer>>();
		/*this.opcs=opcs;
		this.numOPopc=numOPopc;*/

		recorreArbol(gen.raiz);
		//compruebaOpc();
		this.nodos=gen.getNodos(); // Bloating
	}
	
	public IndividuoArbol(IndividuoArbol individuo) {	
		this.gen = new Arbol();
		gen.raiz = duplicaArbol(individuo.gen.raiz);
		this.fitness=0;
		this.nodos=individuo.nodos;

		funcionales = new ArrayList<Pair<Exp,Integer>>();
		Exp invisible = new Salta();
		invisible.setHijo(0, gen.raiz);
		funcionales.add(new Pair<>(invisible, 0));
		terminales = new ArrayList<Pair<Exp,Integer>>();
		
		/*this.opcs=individuo.opcs;
		this.numOPopc=individuo.numOPopc;*/

		operaciones=new ArrayList<String>();
		recorreArbol(gen.raiz);
		//compruebaOpc();
		this.nodos=gen.getNodos(); // Bloating
	}
	
	/*private void compruebaOpc() {
		a=0;
		List<String> nuevasOPS=new ArrayList<String>();
		while(a<operaciones.size()) {
			if(operaciones.get(a).equals("B(")) {
				a++;
				String op="B(;"+operacionRec();							
				nuevasOPS.add(op);
			}
			if(operaciones.get(a).equals("IF(")) {
				a++;
				String op="IF(;"+operacionRec();							
				nuevasOPS.add(op);
			}
			else nuevasOPS.add(operaciones.get(a));			
			a++;
		}
		operaciones=nuevasOPS;
	}
	
	private String operacionRec() {
		String ret="";
		while(true) {
			if(operaciones.get(a).equals("B(")) {
				a++;
				ret+="B(;" + operacionRec()+";";
			}
			else if(operaciones.get(a).equals("IF(")) {
				a++;
				ret+="B(;" + operacionRec()+";";
			}
			else if(operaciones.get(a).charAt(0)=='x'){
				ret+=operaciones.get(a);
				return ret;
			}
			else ret+=operaciones.get(a)+";";
			a++;
		}
	}*/
	
	private Exp duplicaArbol(Exp original) {
		Exp nuevo;
		nuevo = original.duplica();
		for (int i = 0; i < original.getTam(); i++) {
			nuevo.setHijo(i, duplicaArbol(original.getHijo(i)));
		}
		return nuevo;
	}

	public void reiniciaListas(Exp nodo) {
		this.nodos=0;
		
		funcionales = new ArrayList<Pair<Exp,Integer>>();
		Exp invisible = new Salta();
		invisible.setHijo(0, gen.raiz);
		funcionales.add(new Pair<>(invisible, 0));
		terminales = new ArrayList<Pair<Exp,Integer>>();
		

		recorreArbol(nodo);
		//compruebaOpc();
	}

	public void recorreArbol(Exp nodo) {	
		this.nodos++;
		
		//if(nodo.getOperacion().equals("Repeat")) operaciones.add("B(");
		
		for(int i=0;i<nodo.getTam();i++) {				
			recorreArbol(nodo.getHijo(i));
			if(nodo.getHijo(i).getTam() == 0) terminales.add(new Pair<>(nodo, i));
			else funcionales.add(new Pair<>(nodo, i));	
		}	
		
		if(nodo.getOperacion().equals("Avanza")) operaciones.add("A");
		else if(nodo.getOperacion().equals("Izquierda")) operaciones.add("I");
		else if(nodo.getOperacion().equals("Salta")) operaciones.add("S"+nodo.getX()+nodo.getY());
		// Nuevas operaciones opcionales
		else if(nodo.getOperacion().equals("Retrocede")) operaciones.add("R");
		else if(nodo.getOperacion().equals("Derecha")) operaciones.add("D");
		else if(nodo.getOperacion().equals("Mueve_Primer")) operaciones.add("M");
		else if(nodo.getOperacion().equals("Salta_Casilla")) operaciones.add("T"+nodo.getX()+nodo.getY());
		
		//else if(nodo.getOperacion().equals("Repeat")) operaciones.add("x"+nodo.getRepeat()+")");
		
		
		// NO HAY QUE A�ADIR TODAS LOS NODOS, SOLO LOS QUE CONSUMEN TICKS
		// AVANZA IZQUIERDA Y SALTA
		//operaciones.add(nodo.getOp());  
		
	}
	
	public String toString() {
		return ""+gen;
	}
	
}




