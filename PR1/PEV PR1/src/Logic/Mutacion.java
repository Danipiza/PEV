package Logic;


import Model.Individuo;
import Model.IndividuoReal;

//import java.lang.Math.random;

public class Mutacion {

	private double p;
	private int tam_elite;
	
	public Mutacion(double p, int tam_elite) {
		this.p=p;
		this.tam_elite=tam_elite;
	}

	public Individuo[] mut_basicaBin(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
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
	
	
	public Individuo[] mut_Real(Individuo[] poblacion, int prec) {		
		
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		IndividuoReal act;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			act=new IndividuoReal(poblacion[i]);
			for(int j=0;j<poblacion[0].fenotipo.length;j++) {
				if(Math.random()<p) {
					act.fenotipo[j]=act.genAleatorio(prec);
				}
				ret[i]=act;
			}
		}
		
		return ret;
	}
	
	/*
	public Individuo[] mut_basicaReal(Individuo[] poblacion, int precision) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		int prec=precision;
		Individuo act;
		double tmp;
		for (int i=0;i<tam_poblacion-tam_elite;i++) {			
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
	}*/

}
