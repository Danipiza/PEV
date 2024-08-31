/**
 * 
 */
package logic;

import model.Individuo;
import model.IndividuoBin;
import model.IndividuoReal;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for
 * ESP: Clase para
 */

public class Mutacion {
	
	// ENG: Mutation probability.
	// ESP: Probabilidad de mutacion.
	private double p;
	
	// ENG: Elitism size
	// ESP: Tamaño de elitismo
	private int tam_elite;
	
	/**
	 * 
	 * @param p
	 * @param tam_elite
	
	 * ENG: Class constructor
	 * ESP: Constructor de la clase
	 */
	public Mutacion(double p, int tam_elite) {
		this.p=p;
		this.tam_elite=tam_elite;
	}
	
	/**
	 * 
	 * @param poblacion
	 * @return
	
	 * ENG: Basic mutation. Binary
	 * ESP: Mutacion basica. Binario
	 */
	public Individuo[] mut_basicaBin(Individuo[] poblacion) {
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		
		Individuo act;
		
		// ENG: Main loop
		// ESP: Bucle principal
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			// ENG: One individual is chosen for the mutation.
			// ESP: Se escoge un individuo para la mutacion.			
			act=new IndividuoBin(poblacion[i]);
			
			// ENG: The alleles (bits) are changed, with a given probability, to the opposite value.
			// ESP: Se cambian, con una probabilidad dada, los alelos (bits), con el valor opuesto.
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
	
	/**
	 * 
	 * @param poblacion
	 * @param prec
	 * @return
	
	 * ENG: Basic mutation. Real.
	 * ESP: Mutacion basica. Real.
	 */
	public Individuo[] mut_Real(Individuo[] poblacion, int prec) {				
		int tam_poblacion=poblacion.length;
		Individuo[] ret = new Individuo[tam_poblacion];
		
		IndividuoReal act;
		
		// ENG: Main loop
		// ESP: Bucle principal
		for (int i=0;i<tam_poblacion-tam_elite;i++) {
			// ENG: One individual is chosen for the mutation.
			// ESP: Se escoge un individuo para la mutacion.
			act=new IndividuoReal(poblacion[i]);
			
			// ENG: The alleles (bits) are changed, with a given probability, to a random value.
			// ESP: Se cambian, con una probabilidad dada, los alelos (enteros) con valores aleatorios.
			for(int j=0;j<poblacion[0].fenotipo.length;j++) {
				if(Math.random()<p) {
					act.fenotipo[j]=act.gen_aleatorio(prec);
				}
				ret[i]=act;
			}
		}
		
		return ret;
	}
	
	

}

