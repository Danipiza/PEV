package Model;

import java.util.Random;

import Model.Simbolos.Exp;
import Model.Simbolos.Funciones.*;
import Model.Simbolos.Terminales.*;

public class Arbol {
	
	public Exp raiz;
	private int profMax;

	public Arbol(int modo, int profundidad) {
		profMax=profundidad;
		init_raiz();		
		
		switch (modo) {
		case 0: { // CRECIENTE
			creciente(raiz,0);
			break;
		}
		case 1: { // COMPLETO
			completo(raiz,0);
			break;
		}	
		default:
			System.out.println("Valor inexperado al crear arbol: " + modo);
		}
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
				else if(func==4) hijo=new Constante();
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
				else if(func==1) hijo=new Constante();
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
				else if(func==1) hijo=new Constante();
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
