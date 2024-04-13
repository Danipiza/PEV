package Logic;


import java.util.Random;

import Model.Arbol;
import Model.Individuo;
import Model.IndividuoArbol;
import Model.Simbolos.Exp;
import Model.Simbolos.Funciones.Progn;
import Model.Simbolos.Funciones.Suma;
import Model.Simbolos.Terminales.*;

public class Mutacion {

	private double p;	
	private int tam_elite;	
	private Funcion funcion;
	private Random random;
	
	private int filas;
	private int columnas;
	
	public Mutacion(double p, int tam_elite, Funcion funcion, int filas, int columnas) {
		this.p=p;
		this.funcion=funcion;
		this.random=new Random();
		this.filas=filas;
		this.columnas=columnas;
	}
	
	
	public Individuo[] terminal(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		IndividuoArbol[] ret = new IndividuoArbol[tam_poblacion];
				
		IndividuoArbol act=null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=(IndividuoArbol) poblacion[i];		
			if(Math.random()<p) {
				
				int rand=random.nextInt(3);
				Exp newTerminal;
				if(rand==0) newTerminal = new Avanza();
				else if(rand==1) newTerminal = new Constante(filas, columnas);
				else newTerminal = new Izquierda();

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
				
				Exp newFuncional = null;
				if (funcional.getOperacion().equals("Suma")) newFuncional = new Progn();
				else if (funcional.getOperacion().equals("Progn")) newFuncional = new Suma();
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


	public Individuo[] arbol(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		IndividuoArbol[] ret = new IndividuoArbol[tam_poblacion];
		
		
		IndividuoArbol act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act = (IndividuoArbol) poblacion[i];	
			if(Math.random()<p) {
				int tmp=random.nextInt(act.funcionales.size());
				Arbol newArbol = new Arbol(random.nextInt(1), random.nextInt(2) + 2, filas, columnas); //TODO cambiar arbol aleatorio a 3
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
				if (funcional.getOperacion() != "Salta") {
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
				Arbol newArbol = new Arbol(random.nextInt(1), random.nextInt(1) + 2, filas, columnas); //TODO cambiar arbol aleatorio a 3
				act.terminales.get(tmp).getKey().setHijo(act.terminales.get(tmp).getValue(), newArbol.raiz); 

				act.gen.raiz = act.funcionales.get(0).getKey().getHijo(0);
			}

			ret[i]=act;
		}
		
		return ret;
	}


}