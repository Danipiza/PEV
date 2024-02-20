package Logic;

import Model.Individuo;

//import java.lang.Math.random;

public class Mutacion {

	private double p;
	
	public Mutacion(double p) {
		this.p=p;
	}

	public Individuo[] mut_basicaBin(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		Individuo act;
		for (int i=0;i<tam_poblacion;i++) {
			act=poblacion[i];
			//poblacion[i].printIndividuo();
			//System.out.print("muta en: ");
			for(int c=0;c<poblacion[i].genes.length;c++){
				//System.out.print("Gen " + (c+1) + ": ");
				for(int j=0;j<poblacion[i].genes[c].v.length;j++) {					
					if(Math.random()<p) {
						//System.out.print(j+ " ");
						act.genes[c].v[j]=(act.genes[c].v[j]+1)%2;
					}
				}
			}
			//System.out.println();
			//poblacion[i].printIndividuo();
			ret[i]=act;
		}
		return ret;
	}
	
	public Individuo[] mut_basicaReal(Individuo[] poblacion, int precision) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		int prec=precision;
		Individuo act;
		double tmp;
		for (int i=0;i<tam_poblacion;i++) {			
			act=poblacion[i];
			if(Math.random()<p) tmp=Math.random()*3; //[0-3]
			else tmp = act.fenotipo[i]%10;
			while(prec!=0) {
				if(Math.random()<p) {
					// TODO
				}
				else {
					
				}
				prec/=10;
			}
			prec=precision;
			for(int c=0;c<poblacion[i].genes.length;c++){
				tmp=0;
				for(int j=0;j<poblacion[i].genes[c].v.length;j++) {					
					if(Math.random()<p) {
						act.genes[c].v[j]=(act.genes[c].v[j]+1)%2;
					}
				}
				act.fenotipo[c]=tmp;
				tmp=0;
			}
			
			ret[i]=act;
		}
		return ret;
	}

}
