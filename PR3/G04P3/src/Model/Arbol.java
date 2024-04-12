package Model;

import java.util.Random;

import Model.Simbolos.Exp;
import Model.Simbolos.Funciones.*;
import Model.Simbolos.Terminales.*;

public class Arbol {
	
	public Exp raiz;
	private int profMax;
	
	private int filas;
	private int columnas;

	public Arbol(int modo, int profundidad, int filas, int columnas) {
		profMax=profundidad;
		this.filas=filas;
		this.columnas=columnas;
		init_raiz();		
		
		inicializa_arbol(raiz, modo);
	}

	public void inicializa_arbol(Exp exp, int modo) {		
		if (modo==0) completo(exp,0);
		else creciente(exp,0);		
	}
	
	public Arbol(Arbol arbol) {		
		recorreArbol(arbol.raiz);
	}
	
	public void recorreArbol(Exp nodo) {
		
		
	}
	
	private void init_raiz() {
		Random random = new Random();  
		switch (random.nextInt(3)) {
			case 0: {
				this.raiz=new Progn();
				break;
			}
			case 1: {
				this.raiz=new Salta();
				break;
			}
			case 2: {
				this.raiz=new Suma();
				break;
			}
		}
		
	}
	
	
	// Se generan cualquier simbolo hasta la profundiad maxima, que genera terminales
	private Exp creciente(Exp nodo, int profundidad) {
		if(profundidad+1<this.profMax) {								
			
			int hijos=nodo.getTam();
			for(int i=0;i<hijos;i++) {
				Exp hijo=null;
				
				Random random = new Random();
				int func=random.nextInt(3);
				if(func==0) hijo=new Progn();
				else if(func==1) hijo=new Salta();
				else if(func==2) hijo=new Suma();
				else if(func==3) hijo=new Avanza();
				else if(func==4) hijo=new Constante(filas, columnas);
				else hijo=new Izquierda();
				
				nodo.setHijo(i, completo(hijo,profundidad+1));				
			}						
		}
		else { // TERMINALES
			int hijos=nodo.getTam();
			for(int i=0;i<hijos;i++) {
				Exp hijo=null;
				
				Random random = new Random();
				int func=random.nextInt(3);
				if(func==0) hijo=new Avanza();
				else if(func==1) hijo=new Constante(filas, columnas);
				else hijo=new Izquierda();
				
				nodo.setHijo(i, hijo);				
			}
		}
		return nodo;
	}
	
	// Se generan solo funciones hasta la profundiad maxima, que genera terminales
	private Exp completo(Exp nodo, int profundidad) {
		if(profundidad+1<this.profMax) {								
			
			int hijos=nodo.getTam();
			for(int i=0;i<hijos;i++) {
				Exp hijo=null;
				
				Random random = new Random();
				int func=random.nextInt(3);
				if(func==0) hijo=new Progn();
				else if(func==1) hijo=new Salta();
				else hijo=new Suma();
				
				nodo.setHijo(i, completo(hijo,profundidad+1));				
			}						
		}
		else { // TERMINALES
			int hijos=nodo.getTam();
			for(int i=0;i<hijos;i++) {
				Exp hijo=null;
				
				Random random = new Random();
				int func=random.nextInt(3);
				if(func==0) hijo=new Avanza();
				else if(func==1) hijo=new Constante(filas, columnas);
				else hijo=new Izquierda();
				
				nodo.setHijo(i, hijo);				
			}
		}
		return nodo;
	}
	
	
	
	public String toString() {
		return ""+raiz;
	}
	
}
