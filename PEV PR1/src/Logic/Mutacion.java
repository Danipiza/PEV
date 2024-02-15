package Logic;

import Model.Individuo;

//import java.lang.Math.random;

public class Mutacion {	
	
	// TODO 
	public Mutacion() {
		
	}
	
	public Individuo[] mut_basica(Individuo[] poblacion, double p) {
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
	
}
