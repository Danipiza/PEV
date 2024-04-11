package Logic;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Model.Individuo;
import Model.Simbolos.Exp;
import Model.Simbolos.Funciones.Progn;
import Model.Simbolos.Funciones.Salta;
import Model.Simbolos.Funciones.Suma;
import Model.Simbolos.Terminales.*;

public class Mutacion {

	private double p;	
	private int tam_elite;	
	private Funcion funcion;
	private Random random;
	
	public Mutacion(double p, int tam_elite, Funcion funcion) {
		this.p=p;
		this.funcion=funcion;
		random=new Random();
	}
	
	
	public Individuo[] terminal(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
				
		Individuo act=null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			// TODO
			act=poblacion[i];//new Individuo(poblacion[i]);		
			if(Math.random()<p) {
				
				int rand=random.nextInt(3);
				Exp newTerminal;
				if(rand==0) newTerminal = new Avanza();
				if(rand==1) newTerminal = new Constante();
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
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act = null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);		
			//if(Math.random()<p) {
				
				int tmp=random.nextInt(act.funcionales.size());
				Exp funcional = act.funcionales.get(tmp).getKey().getHijo(act.funcionales.get(tmp).getValue());
				
				Exp newFuncional = null;
				if (funcional.getOperacion().equals("Suma")) newFuncional = new Progn();
				else if (funcional.getOperacion().equals("Progn")) newFuncional = new Progn();
				if (newFuncional != null) {
					newFuncional.setHijo(0, funcional.getHijo(0));
					newFuncional.setHijo(1, funcional.getHijo(1));

					act.funcionales.get(tmp).getKey().setHijo(act.funcionales.get(tmp).getValue(), funcional);
				}
				
			//}
			ret[i]=act;
		}
		
		return ret;
	}
	
	
	public Individuo[] permutacion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act = null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);		
			//if(Math.random()<p) {
				int tmp=random.nextInt(act.funcionales.size());
				Exp funcional = act.funcionales.get(tmp).getKey().getHijo(act.funcionales.get(tmp).getValue());
				Exp temp = funcional.getHijo(0);
				funcional.setHijo(0, funcional.getHijo(1));
				funcional.setHijo(1, temp);
			//}
			ret[i]=act;
		}
		
		return ret;
	}

	public Individuo[] hoist(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);		
			//if(Math.random()<p) {
				int tmp=random.nextInt(act.funcionales.size());
				act.gen.raiz = act.funcionales.get(tmp).getKey().getHijo(act.funcionales.get(tmp).getValue());
			//}
			ret[i]=act;
		}
		
		return ret;
	}

	public Individuo[] contraccion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act = null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);		
			//if(Math.random()<p) {

				int rand=random.nextInt(3);
				Exp newExp;
				if(rand==0) newExp = new Avanza();
				if(rand==1) newExp = new Constante();
				else newExp = new Izquierda();
				
				int tmp=random.nextInt(act.funcionales.size());
				act.funcionales.get(tmp).getKey().setHijo(act.funcionales.get(tmp).getValue(), newExp);
				
				
			//}
			ret[i]=act;
		}
		
		return ret;
	}


	public Individuo[] template(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			
		}
		
		return ret;
	}


}
