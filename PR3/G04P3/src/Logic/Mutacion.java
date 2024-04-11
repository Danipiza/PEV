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
			act=new Individuo(poblacion[i]);		
			if(Math.random()<p) {
				int tmp=random.nextInt(act.tamTerminales);				
				dfs_terminal(0,tmp,act.gen.raiz);
			}
			ret[i]=act;
		}
		
		return ret;
	}
	
	
	public int dfs_terminal(int cont, int rand, Exp nodo) {		
		
		if(nodo.getHijo(0).getTam()==0) {
			if (cont==rand) {
		    	int tmp=random.nextInt(3);
		    	if(tmp==0) nodo.setHijo(0, new Avanza());
	    		if(tmp==1) nodo.setHijo(0, new Constante());
	    		else nodo.setHijo(0, new Izquierda());
		        return -1;
		    }
		    cont++;			
		}
		else {
			int tmp=dfs_terminal(cont,rand, nodo.getHijo(0));
			if (tmp==-1) return -1;
			else cont+=tmp;
		}
		
		if(nodo.getTam()==2&&nodo.getHijo(1).getTam()==0) {
			if (cont==rand) {
		    	int tmp=random.nextInt(3);
		    	if(tmp==0) nodo.setHijo(1, new Avanza());
	    		if(tmp==1) nodo.setHijo(1, new Constante());
	    		else nodo.setHijo(1, new Izquierda());
		        return -1;
		    }
		    cont++;			
		}
		else {			
			if(nodo.getTam()==2) {
				int tmp=dfs_terminal(cont,rand, nodo.getHijo(1));
				if(tmp==-1) return -1;
				cont+=tmp;
			}
			
		}
		
		return cont;
	}
	
	// TODO
	public Individuo[] funcional(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act = null;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);		
			//if(Math.random()<p) {
				int tmp=0;
				dfs_funcional(0, tmp, act.gen.raiz, null, -1);
			//}
			ret[i]=act;
		}
		
		return ret;
	}
	
	public Exp funcionalAux(Exp nodo) {
		int n=nodo.getTam();
		Exp[] hijos=new Exp[n];
		for(int i=0;i<n;i++) hijos[i]=nodo.getHijo(i);
		
		Exp nuevo=null;
		int func=random.nextInt(3);
		if(func==0) nuevo=new Progn();
		else if(func==1) nuevo=new Salta();
		else nuevo=new Suma();
		
		return nuevo;
	}
	
	public int dfs_funcional(int cont, int rand, Exp nodo, Exp padre, int nuevoHijo) {		
		
		if (cont==rand) {
			int n=nodo.getTam();
			Exp[] hijos=new Exp[n];
			for(int i=0;i<n;i++) hijos[i]=nodo.getHijo(i);
			
			if(nuevoHijo==-1) {
				int func=random.nextInt(3);
				if(func==0) nodo=new Progn();
				else if(func==1) nodo=new Salta();
				else nodo=new Suma();
				
				for(int i=0;i<n;i++) nodo.setHijo(i, hijos[i]);				
			}
			else {
				Exp nuevo=null;
				int func=random.nextInt(3);
				if(func==0) nuevo=new Progn();
				else if(func==1) nuevo=new Salta();
				else nuevo=new Suma();
				
				for(int i=0;i<n;i++) nuevo.setHijo(i, hijos[i]);
				
				padre.setHijo(nuevoHijo, nuevo);
			}
			return -1;
			
		}
		cont++;
		
		if(nodo.getHijo(0).getTam()!=0) {
		    int tmp=dfs_funcional(cont,rand, nodo.getHijo(0), nodo, 0);
		    if (tmp==-1) return -1;
		    else cont+=tmp;
		}			
		

		if(nodo.getTam()==2&&nodo.getHijo(1).getTam()!=0) {
		    int tmp=dfs_funcional(cont,rand, nodo.getHijo(1),nodo, 1);
		    if (tmp==-1) return -1;
		    else cont+=tmp;
		}
		return cont;
	}
	
	// TODO
	public Individuo[] arbol(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			
		}
		
		return ret;
	}
	
	
	// TODO
	public Individuo[] permutacion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			
		}
		
		return ret;
	}

	public Individuo[] hoist(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			
		}
		
		return ret;
	}

}
