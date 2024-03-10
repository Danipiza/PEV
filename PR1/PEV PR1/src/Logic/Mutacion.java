package Logic;


import Model.Individuo;
import Model.IndividuoBin;
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
			act=new IndividuoBin(poblacion[i]);
			for(int c=0;c<poblacion[i].genes.length;c++){
				for(int j=0;j<poblacion[i].genes[c].v.length;j++) {					
					if(Math.random()<p) {
						act.genes[c].v[j]=(act.genes[c].v[j]+1)%2;
					}
				}
			}
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
	
	

}
