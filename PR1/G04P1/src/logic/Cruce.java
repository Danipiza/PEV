/**
 * 
 */
package logic;

import model.Gen;
import model.Individuo;
import model.IndividuoBin;
import model.IndividuoReal;

/**
 * @author DavidSG & DannyP39
 
 * ENG: Class for Crossing
 * ESP: Clase para Cruce
 */

public class Cruce {
	
	// ENG: Crossing probability.
	// ESP: Probabilidad de cruce.
	private double p;	
	
	// ENG: Elitism size
	// ESP: Tamaño de elitismo
	private int tam_elite;

	/**
	 * 
	 * @param p
	 * @param funcion_idx
	 * @param tam_elite
	
	 * ENG: Class constructor
	 * ESP: Constructor de la clase
	 */
	public Cruce(double p, int funcion_idx, int tam_elite) {
		this.p=p;
		this.tam_elite=tam_elite;
	}
	
	/**
	 * 
	 * @param selec
	 * @return
	
	 * ENG: Mono Point crossing. Binary
	 * ESP: Cruce mono punto. Binario
	 */
	public Individuo[] cruce_monopuntoBin(Individuo[] selec) {
		// ENG: A space for elitism is initialized	
		// ESP: Se inicializa un espacio para el elitismo
		int n=selec.length;
		Individuo[] ret=new Individuo[n+tam_elite]; 
		
		// ENG: Discard the last individual if in not an even selection. 
		//		The individuals are crossed by pairs 
		// ESP: Descarta el ultimo individuo si no es una seleccion par. 
		//		Los individuos se cruzan en parejas.
		if (n%2==1) {
			ret[n-1]=selec[n-1];
			n--; 
		}
		
		// ENG: Calculate the masimum position where it can be cut
		// ESP: Calcula la posicion maxima por donde se puede cortar
		int[] long_genes=new int[selec[0].genes.length];
		int corte_maximo=-1, cont=0;
		for (Gen c: selec[0].genes) {
			corte_maximo+=c.v.length;
			long_genes[cont++]=c.v.length;
		}
		
		int i=0, j=0, k=0;
		Individuo ind1, ind2;
		int corte, tmp;
		
		// ENG: Main loop.
		// ESP: Bucle principal.
		while (i<n) {
			// ENG: Two individuals are chose for the crossing.
			// ESP: Se escogen dos individuos para el cruce.
			ind1=new IndividuoBin(selec[i]);
			ind2=new IndividuoBin(selec[i+1]);
			
			// ENG: With a given probability are crossed.
			// ESP: Con una cierta probabilidad se cruzan.
			if (Math.random()<p) {
				corte=(int) (Math.random()*(corte_maximo))+1;
				cont=0;
				j=0;
				
				// ENG: Alleles (bits) are exchanged until the selected cut.
				// ESP: Se intercambian alelos (bits) hasta el corte seleccionado.
				for (k=0;k<corte;k++) {
					tmp=ind1.genes[cont].v[j];
					ind1.genes[cont].v[j]=ind2.genes[cont].v[j];
					ind2.genes[cont].v[j]=tmp;
					j++;
					if (j==long_genes[cont]) {
						cont++;
						j=0;
					}
				}
			}
			ret[i++]=ind1;
			ret[i++]=ind2;
		}
		return ret;
	}

	/**
	 * 
	 * @param selec
	 * @param d
	 * @return
	
	 * ENG: Mono Point crossing. Real
	 * ESP: Cruce mono punto. Real
	 */
	public Individuo[] cruce_monopuntoReal(Individuo[] selec, int d) {
		// ENG: A space for elitism is initialized	
		// ESP: Se inicializa un espacio para el elitismo
		int n=selec.length;
		Individuo[] ret=new Individuo[n+tam_elite]; 
		
		// ENG: Discard the last individual if in not an even selection. 
		//		The individuals are crossed by pairs 
		// ESP: Descarta el ultimo individuo si no es una seleccion par. 
		//		Los individuos se cruzan en parejas.
		if (n%2==1) {
			ret[n-1]=selec[n-1];
			n--; 
		}

		int corte_maximo=d-1;

		int i=0, j=0;
		Individuo ind1, ind2;
		double corte, tmp;
		
		// ENG: Main loop.
		// ESP: Bucle principal.						
		while (i<n) {
			// ENG: Two individuals are chose for the crossing.
			// ESP: Se escogen dos individuos para el cruce.
			ind1=new IndividuoReal(selec[i]);
			ind2=new IndividuoReal(selec[i+1]);

			// ENG: With a given probability are crossed.
			// ESP: Con una cierta probabilidad se cruzan.
			if (Math.random()<p) {
				corte = (int) (Math.random()*(corte_maximo))+1;
				
				// ENG: Alleles (integers) are exchanged until the selected cut.
				// ESP: Se intercambian alelos (enteros) hasta el corte seleccionado.
				for (j=0;j<corte;j++) {
					tmp=ind1.fenotipo[j];
					ind1.fenotipo[j]=ind2.fenotipo[j];
					ind2.fenotipo[j]=tmp;
				}
			}
			ret[i++]=ind1;
			ret[i++]=ind2;
		}
		return ret;
	}
	
	/**
	 * 
	 * @param selec
	 * @return
	
	 * ENG: Uniform crossing. Binary
	 * ESP: Cruce uniforme. Binario
	 */
	public Individuo[] cruce_uniformeBin(Individuo[] selec) {
		// ENG: A space for elitism is initialized	
		// ESP: Se inicializa un espacio para el elitismo
		int n=selec.length;
		Individuo[] ret=new Individuo[n+tam_elite]; 
		
		// ENG: Discard the last individual if in not an even selection. 
		//		The individuals are crossed by pairs 
		// ESP: Descarta el ultimo individuo si no es una seleccion par. 
		//		Los individuos se cruzan en parejas.
		if (n%2==1) {
			ret[n-1]=selec[n-1];
			n--; 
		}
		
		// ENG: Calculate the masimum position where it can be cut
		// ESP: Calcula la posicion maxima por donde se puede cortar
		int[] long_genes = new int[selec[0].genes.length];
		int cont = 0, l = 0;
		for (Gen c : selec[0].genes) {
			l += c.v.length;
			long_genes[cont++] = c.v.length;
		}

		int i = 0, j = 0, k = 0;
		Individuo ind1, ind2;
		int tmp;
		
		// ENG: Main loop.
		// ESP: Bucle principal.	
		while (i<n) {
			// ENG: Two individuals are chose for the crossing.
			// ESP: Se escogen dos individuos para el cruce.
			ind1=new IndividuoBin(selec[i]);
			ind2=new IndividuoBin(selec[i+1]);
				
			// ENG: With a given probability are crossed.
			// ESP: Con una cierta probabilidad se cruzan.
			if (Math.random()>p) {
				cont=0;
				j=0;
				// ENG: Alleles (bits) are exchanged with a certain probability.
				// ESP: Se intercambian alelos (bits) con una cierta probabilidad.
				for (k=0;k<l;k++) {
					if (Math.random()<0.5) {
						tmp = ind1.genes[cont].v[j];
						ind1.genes[cont].v[j]=ind2.genes[cont].v[j];
						ind2.genes[cont].v[j]=tmp;
					}
					j++;
					if (j==long_genes[cont]) {
						cont++;
						j=0;
					}
				}
			}
			ret[i++]=ind1;
			ret[i++]=ind2;
		}
		return ret;
	}
	
	/**
	 * 
	 * @param selec
	 * @param d
	 * @return
	
	 * ENG: Uniform crossing. Real
	 * ESP: Cruce uniforme. Real
	 */
	public Individuo[] cruce_uniformeReal(Individuo[] selec, int d) {
		// ENG: A space for elitism is initialized	
		// ESP: Se inicializa un espacio para el elitismo
		int n=selec.length;
		Individuo[] ret=new Individuo[n+tam_elite]; 
		
		// ENG: Discard the last individual if in not an even selection. 
		//		The individuals are crossed by pairs 
		// ESP: Descarta el ultimo individuo si no es una seleccion par. 
		//		Los individuos se cruzan en parejas.
		if (n%2==1) {
			ret[n-1]=selec[n-1];
			n--; 
		}

		int i=0, j=0;
		Individuo ind1, ind2;
		double tmp;
		
		// ENG: Main loop.
		// ESP: Bucle principal.	
		while (i<n) {
			// ENG: Two individuals are chose for the crossing.
			// ESP: Se escogen dos individuos para el cruce.
			ind1=new IndividuoReal(selec[i]);
			ind2=new IndividuoReal(selec[i+1]);
			
			// ENG: With a given probability are crossed.
			// ESP: Con una cierta probabilidad se cruzan.
			if (Math.random()<p) {
				
				// ENG: Alleles (integers) are exchanged with a certain probability.
				// ESP: Se intercambian alelos (enteros) con una cierta probabilidad.
				for (j=0;j<d;j++) {
					if (Math.random()<0.5) {
						tmp=ind1.fenotipo[j];
						ind1.fenotipo[j]=ind2.fenotipo[j];
						ind2.fenotipo[j]=tmp;
					}
				}
			}
			ret[i++]=ind1;
			ret[i++]=ind2;
		}
		return ret;
	}
	
	/**
	 * 
	 * @param selec
	 * @param d
	 * @param a
	 * @return
	
	 * ENG: Arithmetic crossing. Real
	 * ESP: Cruce aritmetico. Real
	 */
	public Individuo[] cruce_aritmetico(Individuo[] selec, int d, double a) {
		// ENG: A space for elitism is initialized	
		// ESP: Se inicializa un espacio para el elitismo
		int n=selec.length;
		Individuo[] ret=new Individuo[n+tam_elite]; 
		
		// ENG: Discard the last individual if in not an even selection. 
		//		The individuals are crossed by pairs 
		// ESP: Descarta el ultimo individuo si no es una seleccion par. 
		//		Los individuos se cruzan en parejas.
		if (n%2==1) {
			ret[n-1]=selec[n-1];
			n--; 
		}

		int i=0, j=0;
		Individuo ind1, ind2;
		double tmp1, tmp2;
		
		// ENG: Main loop.
		// ESP: Bucle principal.	
		while (i<n) {
			// ENG: Two individuals are chose for the crossing.
			// ESP: Se escogen dos individuos para el cruce.
			ind1=new IndividuoReal(selec[i]);
			ind2=new IndividuoReal(selec[i+1]);
			
			// ENG: With a given probability are crossed.
			// ESP: Con una cierta probabilidad se cruzan.
			if (Math.random()<p) {
				
				// ENG: Values ​​are exchanged multiplied by a mathematical formula.
				// ESP: Se intercambian valores multiplicados una formula matematica.
				for (j=0;j<d;j++) {
					tmp1=ind1.fenotipo[j]*a+ind2.fenotipo[j]*(1-a);
					tmp2=ind2.fenotipo[j]*a+ind1.fenotipo[j]*(1-a);
					ind1.fenotipo[j]=tmp1;
					ind2.fenotipo[j]=tmp2;
				}
			}
			ret[i++]=ind1;
			ret[i++]=ind2;
		}
		return ret;
	}
	
	
	/**
	 * 
	 * @param selec
	 * @param d
	 * @param a
	 * @return
	
	 * ENG: BLX crossing. Real
	 * ESP: Cruce BLX. Real
	 */
	public Individuo[] cruce_BLX(Individuo[] selec, int d, double a) {
		// ENG: A space for elitism is initialized	
		// ESP: Se inicializa un espacio para el elitismo
		int n=selec.length;
		Individuo[] ret=new Individuo[n+tam_elite]; 
		
		// ENG: Discard the last individual if in not an even selection. 
		//		The individuals are crossed by pairs 
		// ESP: Descarta el ultimo individuo si no es una seleccion par. 
		//		Los individuos se cruzan en parejas.
		if (n%2==1) {
			ret[n-1]=selec[n-1];
			n--; 
		}

		int i=0, j=0;
		Individuo ind1, ind2;
		double cMax, cMin, I;
		
		// ENG: Main loop.
		// ESP: Bucle principal.	
		while (i<n) {
			// ENG: Two individuals are chose for the crossing.
			// ESP: Se escogen dos individuos para el cruce.
			ind1=new IndividuoReal(selec[i]);
			ind2=new IndividuoReal(selec[i+1]);
			
			// ENG: With a given probability are crossed.
			// ESP: Con una cierta probabilidad se cruzan.
			if (Math.random()<p) {
				
				// ENG: Values ​​are exchanged multiplied by a mathematical formula.
				// ESP: Se intercambian valores multiplicados una formula matematica.
				for (j=0;j<d;j++) {
					cMax=(ind1.fenotipo[j]>ind2.fenotipo[j]? ind1.fenotipo[j]:ind2.fenotipo[j]);
					cMin=(ind1.fenotipo[j]<ind2.fenotipo[j]? ind1.fenotipo[j]:ind2.fenotipo[j]);
					I=cMax-cMin;
					ind1.fenotipo[j]=cMin+Math.random()*I;
					ind2.fenotipo[j]=cMin+Math.random()*I;
				}
			}
			ret[i++]=ind1;
			ret[i++]=ind2;
		}
		return ret;
	}
	
	
}

