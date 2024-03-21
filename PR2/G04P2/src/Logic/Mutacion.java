package Logic;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Model.Individuo;

public class Mutacion {

	private double p;
	private int num_vuelos;
	private int tam_elite;
	List<List<Integer>> permutaciones;
	
	Funcion funcion;
	
	public Mutacion(double p, int num_vuelos, int tam_elite, Funcion funcion) {
		this.p=p;
		this.num_vuelos=num_vuelos;
		this.tam_elite=tam_elite;
		this.funcion=funcion;
	}

	public Individuo[] inversion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);		
			if(Math.random()<p) {
				int corte1 = (int) (Math.random() * (act.gen.v.length-2));
				int corte2 = corte1 + (int) (Math.random() * (act.gen.v.length-corte1));
				int separacion = (corte2-corte1+1);
				for (int k = 0; k < separacion/2; k++) {
					int temp = act.gen.v[corte1+k];
					act.gen.v[corte1+k] = act.gen.v[corte2-k];
					act.gen.v[corte2-k] = temp;
				}
			}
			ret[i]=act;
		}
		return ret;
	}

	public Individuo[] intercambio(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);				
			if(Math.random()<p) {
				int punto1 = (int) (Math.random() * (act.gen.v.length-2));
				int punto2 = punto1 + (int) (Math.random() * (act.gen.v.length-1-punto1));
				int temp = act.gen.v[punto1];
				act.gen.v[punto1] = act.gen.v[punto2];
				act.gen.v[punto2] = temp;
			}
			ret[i]=act;
		}
		return ret;
	}

	public Individuo[] insercion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);		
			if(Math.random()<p) {
				int antiguaPosicion = (int) (Math.random() * (act.gen.v.length-1));

				int nuevaPosicion = antiguaPosicion;
				while (nuevaPosicion == antiguaPosicion) {
					nuevaPosicion = (int) (Math.random() * (act.gen.v.length-1));
				}				
				if (nuevaPosicion > antiguaPosicion) {
					int tmp = act.gen.v[antiguaPosicion];
					for (int k = antiguaPosicion; k < nuevaPosicion; k++) {
						act.gen.v[k] = act.gen.v[k+1];
					}
					act.gen.v[nuevaPosicion] = tmp;
				}
				else {
					int tmp = act.gen.v[antiguaPosicion];
					for (int k = antiguaPosicion; k > nuevaPosicion ; k--) {
						act.gen.v[k] = act.gen.v[k-1];
					}
					act.gen.v[nuevaPosicion] = tmp;
				}				
			}
			ret[i]=act;
		}
		return ret;
	}

	public Individuo[] heuristica(Individuo[] poblacion, int n) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];		
		
		Random rand = new Random();
		
		int cont=0;		
		int[] elegidos_indx=new int[n];
		int[] elegidos_vals=new int[n];		
		Set<Integer> set_elegidos=new HashSet<Integer>();
		
		permutaciones=new ArrayList<List<Integer>>();
		dfs(n,0,new ArrayList<Integer>(), new int[n]);			
		
		double mejor_fit=Double.MAX_VALUE;		
		int[] mejor=new int[num_vuelos];
		int[] tmp=new int[num_vuelos];
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);
							
			if(Math.random()<p) {
				cont=0;
				set_elegidos.clear();
				mejor_fit=Double.MAX_VALUE;
				
				while(cont<n) {
					int numero = rand.nextInt(num_vuelos);
					if(!set_elegidos.contains(numero)) {
						set_elegidos.add(numero);
						elegidos_indx[cont]=numero;
						elegidos_vals[cont++]=act.gen.v[numero];
					}						
				}
				
				tmp=act.gen.v;
				// Recorre las permutaciones
				for (List<Integer> l:permutaciones) {
					// Cambia los valores
					for(int x:l) tmp[elegidos_indx[x]]=elegidos_vals[x];					
					
					double fit =funcion.fitness(act.gen.v);					
					if(fit<mejor_fit) {
						mejor_fit=fit;
						mejor=tmp;
					}
				}
				
				act=new Individuo(mejor);				
			}
			
			ret[i]=act;
		}
		return ret;
	}
	
	
	// Funcion que calcula las posibles permutaciones, de los indices, es decir,
	//		0,1,2 - 0,2,1 - 1,0,2 - 1,2,0 - 2,0,1 - 2,1,0. 
	//		Solo se invoca una vez por generacion (se puede mejorar)
	private void dfs(int n, int k, List<Integer> act, int[] visitados) {
		if(k==n) {
			List<Integer> aux=new ArrayList<Integer>();
			for(int x:act) {
				aux.add(x);
			}
			permutaciones.add(aux);
			return;
		}
		
		for(int i=0;i<n;i++) {
			if(visitados[i]==1) continue;
			visitados[i]=1;
			act.add(i);
			dfs(n,k+1,act,visitados);
			act.remove(act.size()-1);			
			visitados[i]=0;
		}
	}

	static void swap(int x, int y) {
		int temp = x;
		x = y;
		y = temp;
	}
	
	
	public Individuo[] custom(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new Individuo(poblacion[i]);		
			if(Math.random()<p) {
				int corte1 = (int) (Math.random() * (act.gen.v.length-2));
				int corte2 = corte1 + (int) (Math.random() * (act.gen.v.length-corte1));
				List<Integer> tmp=new ArrayList<Integer>();
				for(int j=corte1;j<corte2;j++) {
					tmp.add(act.gen.v[j]);
				}
				int indice=0;
				for(;corte1<corte2;corte1++) {
					indice=(int) (Math.random() * (tmp.size()));
					act.gen.v[corte1]=tmp.get(indice);
					tmp.remove(indice);
				}
				
			}
			
			ret[i]=act;
		}
		return ret;
	}

}
