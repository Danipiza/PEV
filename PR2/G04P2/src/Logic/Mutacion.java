package Logic;


import Model.Individuo;

public class Mutacion {

	private double p;
	private int tam_elite;
	
	public Mutacion(double p, int tam_elite) {
		this.p=p;
		this.tam_elite=tam_elite;
	}

	public Individuo[] inversion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=poblacion[i];
			poblacion[i].printIndividuo();
			//System.out.print("muta en: ");			
			if(Math.random()<p) {
				int corte1 = (int) (Math.random() * (act.gen.v.length-2));
				int corte2 = corte1 + (int) (Math.random() * (act.gen.v.length-corte1));
				System.out.println("Corte en: " + corte1 + " " + corte2);
				int separacion = (corte2-corte1+1);
				for (int k = 0; k < separacion/2; k++) {
					int temp = act.gen.v[corte1+k];
					act.gen.v[corte1+k] = act.gen.v[corte2-k];
					act.gen.v[corte2-k] = temp;
				}
			}
			System.out.println();
			poblacion[i].printIndividuo();
			ret[i]=act;
		}
		return ret;
	}

	public Individuo[] intercambio(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=poblacion[i];
			poblacion[i].printIndividuo();
			//System.out.print("muta en: ");				
			if(Math.random()<p) {
				int punto1 = (int) (Math.random() * (act.gen.v.length-2));
				int punto2 = punto1 + (int) (Math.random() * (act.gen.v.length-1-punto1));
				System.out.println("Punto 1: " + punto1 + " Punto2: "+ punto2);
				int temp = act.gen.v[punto1];
				act.gen.v[punto1] = act.gen.v[punto2];
				act.gen.v[punto2] = temp;
			}
			//System.out.println();
			poblacion[i].printIndividuo();
			ret[i]=act;
		}
		return ret;
	}

	public Individuo[] insercion(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=poblacion[i];
			//poblacion[i].printIndividuo();
			//System.out.print("muta en: ");				
			if(Math.random()<p) {
				int antiguaPosicion = (int) (Math.random() * (act.gen.v.length-1));

				int nuevaPosicion = antiguaPosicion;
				while (nuevaPosicion == antiguaPosicion) {
					nuevaPosicion = (int) (Math.random() * (act.gen.v.length-1));
				}
				//System.out.println("Ant: "+ antiguaPosicion + " Nue: " + nuevaPosicion);
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
			//System.out.println();			
			//poblacion[i].printIndividuo();
			ret[i]=act;
		}
		return ret;
	}

	public Individuo[] heuristica(Individuo[] poblacion, int n) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=poblacion[i];
			//poblacion[i].printIndividuo();
			//System.out.print("muta en: ");
			for(int j=0;j<poblacion[i].gen.v.length;j++) {					
				if(Math.random()<p) {
					

					
				}
			}
			//System.out.println();
			//poblacion[i].printIndividuo();
			ret[i]=act;
		}
		return ret;
	}

	static void swap(int x, int y) {
		int temp = x;
		x = y;
		y = temp;
	}
	
	public Individuo[] custom(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		return ret;
	}

}
