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
	
	private int nodos; // Control de Bloating
	
	private boolean[] opcs;
	private int OPopc;
	
	private int numOPopcF;
	private int numOPopcT;
	
	private boolean repeat;

	public Arbol(int modo, int profundidad, int filas, int columnas, 
			boolean[] opcs, int numOPopcF,int numOPopcT) {
		profMax=profundidad;
		this.filas=filas;
		this.columnas=columnas;
		this.nodos=0;
		
		this.opcs=opcs;
		this.numOPopcF=numOPopcF;
		this.numOPopcT=numOPopcT;
		this.OPopc=(numOPopcF+numOPopcT>=1?1:0);
		
		this.repeat=false;
		
		init_raiz();		
		
		inicializa_arbol(raiz, modo);
	}

	public Arbol() {
	}

	public void inicializa_arbol(Exp exp, int modo) {		
		if (modo==0) completo(exp,0);
		else creciente(exp,0);		
	}
	
	/*public Arbol(Arbol arbol) {	
		this.nodos=0;
		recorreArbol(arbol.raiz);
	}
	
	public void recorreArbol(Exp nodo) {
		
		
	}*/
	
	private void init_raiz() {
		this.nodos++;
		Random random = new Random();
		
		switch (random.nextInt(3+(numOPopcF>=1?1:0))) {
			case 0: 
				this.raiz=new Progn();
				break;
			
			case 1: 
				this.raiz=new Salta();
				break;
			
			case 2: 
				this.raiz=new Suma();
				break;
			
			case 3:
				int num=random.nextInt(numOPopcF+(repeat?-1:0));
				
				int cont=0;
				/*if(opcs[4]) {
					if(cont==num) {
						this.raiz=new Opc_IF_Dirty();
						return;
					}
					cont++;
				}
				if(opcs[5]&&!repeat) {
					repeat=true;
					if(cont==num) {
						this.raiz=new Opc_Repeat();
						return;
					}
					cont++;
				}*/
				if(opcs[4]) {
					if(cont==num) {
						this.raiz=new Opc_Salto_A();
						return;
					}
				}
					
						
				break;
			
		}
		
	}
	
	
	// Se generan cualquier simbolo hasta la profundiad maxima, que genera terminales
	private Exp creciente(Exp nodo, int profundidad) {
		if(profundidad+1<this.profMax) {								
			
			int hijos=nodo.getTam();
			for(int i=0;i<hijos;i++) {
				Exp hijo=null;
				
				this.nodos++;
				
				Random random = new Random();
				int func=random.nextInt(6+OPopc);
				if(func==0) hijo=new Progn();
				else if(func==1) hijo=new Salta();
				else if(func==2) hijo=new Suma();
				else if(func==3) hijo=new Avanza();
				else if(func==4) hijo=new Constante(filas, columnas);
				else if(func==5) hijo=new Izquierda();
				else {
					int num=random.nextInt(numOPopcF+numOPopcT+(repeat?-1:0));
					int cont=0;
					if(opcs[4]) {
						if(cont==num) hijo=new Opc_Salto_A();
						cont++;
						
					}
					/*if(opcs[5]) {
						if(cont==num) hijo=new Opc_IF_Dirty();						
						cont++;
						
					}
					if(opcs[6]&&!repeat) {
						repeat=true;
						if(cont==num) hijo=new Opc_Repeat();					
						cont++;
					}*/
					
					if(opcs[1]) {
						if(cont==num) hijo=new Opc_Derecha();
						cont++;
					}
					if(opcs[2]) {
						if(cont==num) hijo=new Opc_Mueve_Primer();
						cont++;
					}
					if(opcs[3]) {
						if(cont==num) hijo=new Opc_Retrocede();
						cont++;
					}
				}
				
				nodo.setHijo(i, completo(hijo,profundidad+1));				
			}	
			//if(nodo.getOperacion().equals("Repeat")) repeat=false;
		}
		else { // TERMINALES
			int hijos=nodo.getTam();
			for(int i=0;i<hijos;i++) {
				Exp hijo=null;
				
				this.nodos++;
				
				Random random = new Random();
				int func=random.nextInt(3+(numOPopcT>=1?1:0));
				if(func==0) hijo=new Avanza();
				else if(func==1) hijo=new Constante(filas, columnas);
				else if(func==2) hijo=new Izquierda();
				else {
					int num=random.nextInt(numOPopcT);
					int cont=0;
					if(opcs[1]) {
						if(cont==num) hijo=new Opc_Derecha();
						cont++;
					}
					if(opcs[2]) {
						if(cont==num) hijo=new Opc_Mueve_Primer();
						cont++;
					}
					if(opcs[3]) {
						if(cont==num) hijo=new Opc_Retrocede();
						cont++;
					}
				}
				
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
				
				this.nodos++;
				
				Random random = new Random();
				int func=random.nextInt(3+(numOPopcF>=1?1:0));//+(repeat?-1:0));
				if(func==0) hijo=new Progn();
				else if(func==1) hijo=new Salta();
				else if(func==2) hijo=new Suma();
				else hijo=new Opc_Salto_A(); 
				/*{
					//int num=random.nextInt(numOPopcF);
					//int cont=0;
					if(opcs[4]) {
						if(cont==num) hijo=new Opc_Salto_A();
						cont++;
						
					}
					/*if(opcs[5]) {
						if(cont==num) hijo=new Opc_IF_Dirty();						
						cont++;
						
					}
					if(opcs[6]&&!repeat) {
						repeat=true;
						if(cont==num) hijo=new Opc_Repeat();					
						cont++;
					}
				}*/
				
				nodo.setHijo(i, completo(hijo,profundidad+1));				
			}		
			//if(nodo.getOperacion().equals("Repeat")) repeat=false;
		}
		else { // TERMINALES
			int hijos=nodo.getTam();
			for(int i=0;i<hijos;i++) {
				Exp hijo=null;
				
				this.nodos++;
				
				Random random = new Random();
				int func=random.nextInt(3+(numOPopcT>=1?1:0));
				if(func==0) hijo=new Avanza();
				else if(func==1) hijo=new Constante(filas, columnas);
				else if(func==2) hijo=new Izquierda();
				else {
					int num=random.nextInt(numOPopcT);
					int cont=0;
					if(opcs[1]) {
						if(cont==num) hijo=new Opc_Derecha();
						cont++;
					}
					if(opcs[2]) {
						if(cont==num) hijo=new Opc_Mueve_Primer();
						cont++;
					}
					if(opcs[3]) {
						if(cont==num) hijo=new Opc_Retrocede();
						cont++;
					}
				}
				
				nodo.setHijo(i, hijo);				
			}
		}
		return nodo;
	}
	
	public int getNodos() { return this.nodos; }
	
	public String toString() {
		return ""+raiz;
	}
	
}
