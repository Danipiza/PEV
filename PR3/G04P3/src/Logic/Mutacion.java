package Logic;


import java.util.Random;

import Model.Arbol;
import Model.Individuo;
import Model.IndividuoArbol;
import Model.Simbolos.Exp;
import Model.Simbolos.Funciones.Opc_Salto_A;
import Model.Simbolos.Funciones.Progn;
import Model.Simbolos.Funciones.Salta;
import Model.Simbolos.Funciones.Suma;
import Model.Simbolos.Terminales.*;

public class Mutacion {

	private double p;	
	private int tam_elite;	
	private Funcion funcion;
	private Random random;
	
	private int filas;
	private int columnas;
	
	private boolean[] opcs;
	private int numOPopcF;
	private int numOPopcT;
	
	public Mutacion(double p, int tam_elite, Funcion funcion, int filas, int columnas, 
			boolean[] opcs, int numOPopcF,int numOPopcT) {
		this.p=p;
		this.funcion=funcion;
		this.random=new Random();
		this.filas=filas;
		this.columnas=columnas;
		
		this.tam_elite=tam_elite;
		
		this.opcs=opcs;
		this.numOPopcF=numOPopcF;
		this.numOPopcT=numOPopcT;
	}
	
	
	public Individuo[] terminal(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		IndividuoArbol[] ret = new IndividuoArbol[tam_poblacion];
				
		IndividuoArbol act=null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=(IndividuoArbol) poblacion[i];		
			if(Math.random()<p) {
				
				int rand=random.nextInt(3+(numOPopcT!=0?1:0));
				Exp newTerminal=null;
				if(rand==0) newTerminal = new Avanza();
				else if(rand==1) newTerminal = new Constante(filas, columnas);
				else if(rand==2) newTerminal = new Izquierda();
				else {
					int num=random.nextInt(numOPopcT);
					int cont=0;
					if(opcs[1]) {
						if(cont==num) newTerminal=new Opc_Derecha();
						cont++;
					}
					if(opcs[2]) {
						if(cont==num) newTerminal=new Opc_Mueve_Primer();
						cont++;
					}
					if(opcs[3]) {
						if(cont==num) newTerminal=new Opc_Retrocede();
						cont++;
					}
					
				}

				int tmp=random.nextInt(act.terminales.size());		
				act.terminales.get(tmp).getKey().setHijo(act.terminales.get(tmp).getValue(), newTerminal);
				
			}
			ret[i]=act;
		}
		
		return ret;
	}
	
	
	public Individuo[] funcional(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		IndividuoArbol[] ret = new IndividuoArbol[tam_poblacion];
		
		
		IndividuoArbol act = null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act = (IndividuoArbol) poblacion[i];		
			if(Math.random()<p) {
				
				int tmp=random.nextInt(act.funcionales.size());
				Exp funcional = act.funcionales.get(tmp).getKey().getHijo(act.funcionales.get(tmp).getValue());
				//if(funcional.getOperacion().equals("Repeat")) continue; // NO SE INTERCAMBIAN BUCLES
				Exp newFuncional = null;
				if (funcional.getOperacion().equals("Suma")) newFuncional = new Progn();
				else if (funcional.getOperacion().equals("Progn")) newFuncional = new Suma();
				else if (funcional.getOperacion().equals("Salto")) newFuncional = new Opc_Salto_A();
				else if (funcional.getOperacion().equals("Salta_Casilla")) newFuncional = new Salta();
				if (newFuncional != null) {
					newFuncional.setHijo(0, funcional.getHijo(0));
					newFuncional.setHijo(1, funcional.getHijo(1));

					act.funcionales.get(tmp).getKey().setHijo(act.funcionales.get(tmp).getValue(), newFuncional);

					act.gen.raiz = act.funcionales.get(0).getKey().getHijo(0);
				}
				
			}
			ret[i]=act;
		}
		
		return ret;
	}

	// NO CAMBIA
	public Individuo[] arbol(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		IndividuoArbol[] ret = new IndividuoArbol[tam_poblacion];
		
		
		IndividuoArbol act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act = (IndividuoArbol) poblacion[i];	
			if(Math.random()<p) {
				int tmp=random.nextInt(act.funcionales.size());
				Arbol newArbol = new Arbol(random.nextInt(1), random.nextInt(2) + 2, filas, columnas, opcs, numOPopcF,numOPopcT); //TODO cambiar arbol aleatorio a 3
				act.funcionales.get(tmp).getKey().setHijo(act.funcionales.get(tmp).getValue(), newArbol.raiz);
				
				act.gen.raiz = act.funcionales.get(0).getKey().getHijo(0);
			}
			ret[i]=act;
		}
		
		return ret;
	}
	
	
	public Individuo[] permutacion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		IndividuoArbol[] ret = new IndividuoArbol[tam_poblacion];
		
		
		IndividuoArbol act = null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act = (IndividuoArbol) poblacion[i];		
			if(Math.random()<p) {
				int tmp=random.nextInt(act.funcionales.size());
				Exp funcional = act.funcionales.get(tmp).getKey().getHijo(act.funcionales.get(tmp).getValue());
				if (funcional.getOperacion().equals("Progn") || funcional.getOperacion().equals("Suma")) {
					Exp temp = funcional.getHijo(0);
					funcional.setHijo(0, funcional.getHijo(1));
					funcional.setHijo(1, temp);
				}
				
			}
			ret[i]=act;
		}
		
		return ret;
	}
	
	
	public Individuo[] hoist(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		IndividuoArbol[] ret = new IndividuoArbol[tam_poblacion];
		
		
		IndividuoArbol act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act = (IndividuoArbol) poblacion[i];		
			if(Math.random()<p) {
				int tmp=random.nextInt(act.funcionales.size());
				act.gen.raiz = act.funcionales.get(tmp).getKey().getHijo(act.funcionales.get(tmp).getValue());
			}
			ret[i]=act;
		}
		
		return ret;
	}
	
	
	public Individuo[] contraccion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		IndividuoArbol[] ret = new IndividuoArbol[tam_poblacion];
		
		
		IndividuoArbol act = null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act = (IndividuoArbol) poblacion[i];		
			if(Math.random()<p) {

				int rand=random.nextInt(3);
				Exp newExp;
				if(rand==0) newExp = new Avanza();
				if(rand==1) newExp = new Constante(filas, columnas);
				else newExp = new Izquierda();
				
				int tmp=random.nextInt(act.funcionales.size());
				act.funcionales.get(tmp).getKey().setHijo(act.funcionales.get(tmp).getValue(), newExp);
				
				
			}
			ret[i]=act;
		}
		
		return ret;
	}

	
	public Individuo[] expansion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		IndividuoArbol[] ret = new IndividuoArbol[tam_poblacion];
		
		
		IndividuoArbol act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act = (IndividuoArbol) poblacion[i];	
			if(Math.random()<p) {
				int tmp=random.nextInt(act.terminales.size());
				Arbol newArbol = new Arbol(random.nextInt(1), random.nextInt(1) + 2, filas, columnas, opcs, numOPopcF,numOPopcT); //TODO cambiar arbol aleatorio a 3
				act.terminales.get(tmp).getKey().setHijo(act.terminales.get(tmp).getValue(), newArbol.raiz); 

				act.gen.raiz = act.funcionales.get(0).getKey().getHijo(0);
			}

			ret[i]=act;
		}
		
		return ret;
	}


}